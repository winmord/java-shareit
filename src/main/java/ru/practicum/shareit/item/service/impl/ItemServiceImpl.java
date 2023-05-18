package ru.practicum.shareit.item.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.model.ShortBooking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.error.ItemNotFoundException;
import ru.practicum.shareit.error.ItemUnavailableException;
import ru.practicum.shareit.error.UserAccessDeniedException;
import ru.practicum.shareit.error.UserNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, BookingRepository bookingRepository, CommentRepository commentRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public ItemDto createItem(ItemDto itemDto, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Пользователь " + userId + " не существует");
        }

        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(user.get());

        Item createdItem = itemRepository.save(item);
        logger.info("Создана вещь с id={}", createdItem.getId());

        return ItemMapper.toItemDto(createdItem);
    }

    @Override
    @Transactional
    public ItemDto changeItem(Long itemId, ItemDto itemDto, Long userId) {
        Optional<Item> item = itemRepository.findById(itemId);

        if (item.isEmpty()) {
            throw new ItemNotFoundException("Вещь " + itemId + " не найдена");
        }

        Long ownerId = item.get().getOwner().getId();
        if (!Objects.equals(ownerId, userId)) {
            throw new UserAccessDeniedException("У пользователя " + userId + " нет прав на изменение вещи " + itemId);
        }

        String itemName = itemDto.getName();
        String itemDescription = itemDto.getDescription();
        Boolean itemIsAvailable = itemDto.getAvailable();

        Item repositoryItem = item.get();

        if (itemName != null && !Objects.equals(itemName, repositoryItem.getName())) {
            repositoryItem.setName(itemName);
        }

        if (itemDescription != null && !Objects.equals(itemDescription, repositoryItem.getDescription())) {
            repositoryItem.setDescription(itemDescription);
        }

        if (itemIsAvailable != null && !Objects.equals(itemIsAvailable, repositoryItem.getAvailable())) {
            repositoryItem.setAvailable(itemIsAvailable);
        }

        Item changedItem = itemRepository.save(repositoryItem);
        logger.info("Обновлена вещь с id={}", changedItem.getId());

        return ItemMapper.toItemDto(changedItem);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDto getItem(Long itemId, Long userId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (optionalItem.isEmpty()) {
            throw new ItemNotFoundException("Вещь " + itemId + " не найдена");
        }

        Item item = optionalItem.get();
        addItemBookings(item, userId);

        Collection<CommentDto> comments = commentRepository.findAllByItemId(itemId).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());

        logger.info("Запрошена вещь с id={}", item.getId());

        return ItemMapper.toItemDtoWithComments(item, comments);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemDto> getAllItems(Long userId) {
        Optional<User> owner = userRepository.findById(userId);

        if (owner.isEmpty()) {
            throw new UserNotFoundException("Пользователь " + userId + " не существует");
        }

        Collection<Item> items = itemRepository.findAllByOwner(owner.get());

        Collection<ItemDto> itemDtos = new ArrayList<>();

        for (Item item : items) {
            addItemBookings(item, userId);

            Collection<CommentDto> comments = commentRepository.findAllByItemId(item.getId()).stream()
                    .map(CommentMapper::toCommentDto)
                    .collect(Collectors.toList());

            itemDtos.add(ItemMapper.toItemDtoWithComments(item, comments));
        }

        logger.info("Запрошено {} вещей", items.size());

        return itemDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemDto> searchItems(String text) {
        Collection<ItemDto> items = itemRepository.search(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());

        logger.info("Найдено {} вещей", items.size());

        return items;
    }

    @Override
    @Transactional
    public CommentDto addComment(Long itemId, CommentDto commentDto, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("Пользователь " + userId + " не существует");
        }

        User user = optionalUser.get();

        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (optionalItem.isEmpty()) {
            throw new ItemNotFoundException("Вещь " + itemId + " не существует");
        }

        Item item = optionalItem.get();

        if (bookingRepository.findAllByBookerIdAndItemIdAndEndBefore(userId, itemId, LocalDateTime.now()).isEmpty()) {
            throw new ItemUnavailableException("Пользователь " + userId + " не бронировал вещь " + itemId);
        }

        Comment comment = CommentMapper.toComment(commentDto);

        comment.setAuthor(user);
        comment.setItem(item);
        comment.setCreated(LocalDateTime.now());

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    private void addItemBookings(Item item, Long userId) {
        if (Objects.equals(item.getOwner().getId(), userId)) {
            LocalDateTime now = LocalDateTime.now();

            ShortBooking lastBooking = bookingRepository.findFirstByItemIdAndStartBeforeOrderByStartDesc(item.getId(), now);
            ShortBooking nextBooking = bookingRepository.findFirstByItemIdAndStartAfterAndStatusNotOrderByStartAsc(item.getId(), now, BookingStatus.REJECTED);
            item.setLastBooking(lastBooking);
            item.setNextBooking(nextBooking);
        }
    }
}
