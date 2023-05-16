package ru.practicum.shareit.item.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.ItemNotFoundException;
import ru.practicum.shareit.error.UserAccessDeniedException;
import ru.practicum.shareit.error.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
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
    public ItemDto getItem(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);

        if (item.isEmpty()) {
            throw new ItemNotFoundException("Вещь " + itemId + " не найдена");
        }

        Item gotItem = item.get();
        logger.info("Запрошена вещь с id={}", gotItem.getId());

        return ItemMapper.toItemDto(gotItem);
    }

    @Override
    public Collection<ItemDto> getAllItems(Long userId) {
        Optional<User> owner = userRepository.findById(userId);

        if (owner.isEmpty()) {
            throw new UserNotFoundException("Пользователь " + userId + " не существует");
        }

        Collection<ItemDto> items = itemRepository.findAllByOwner(owner.get()).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());

        logger.info("Запрошено {} вещей", items.size());

        return items;
    }

    @Override
    public Collection<ItemDto> searchItems(String text) {
        Collection<ItemDto> items = itemRepository.search(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());

        logger.info("Найдено {} вещей", items.size());

        return items;
    }
}
