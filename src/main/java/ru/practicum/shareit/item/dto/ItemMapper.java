package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.user.dto.UserMapper;

import java.util.Collection;

public class ItemMapper {
    private ItemMapper() {
        throw new IllegalStateException("Mapper class");
    }

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .owner(UserMapper.toUserDto(item.getOwner()))
                .available(item.getAvailable())
                .request(ItemRequestMapper.toItemRequestDto(item.getRequest()))
                .lastBooking(item.getLastBooking())
                .nextBooking(item.getNextBooking())
                .build();
    }

    public static ItemDto toItemDtoWithComments(Item item, Collection<CommentDto> comments) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .owner(UserMapper.toUserDto(item.getOwner()))
                .available(item.getAvailable())
                .request(ItemRequestMapper.toItemRequestDto(item.getRequest()))
                .lastBooking(item.getLastBooking())
                .nextBooking(item.getNextBooking())
                .comments(comments)
                .build();
    }

    public static Item toItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .lastBooking(itemDto.getLastBooking())
                .nextBooking(itemDto.getNextBooking())
                .build();
    }
}
