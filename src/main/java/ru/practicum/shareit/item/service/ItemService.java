package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, Long userId);

    ItemDto changeItem(Long itemId, ItemDto itemDto, Long userId);

    ItemDto getItem(Long itemId, Long userId);

    Collection<ItemDto> getAllItems(Long userId);

    Collection<ItemDto> searchItems(String text);

    CommentDto addComment(Long itemId, CommentDto commentDto, Long userId);
}
