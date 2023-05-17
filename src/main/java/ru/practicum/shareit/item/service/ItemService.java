package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;

import java.util.Collection;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, Long userId);

    ItemDto changeItem(Long itemId, ItemDto itemDto, Long userId);

    ItemDto getItem(Long itemId, Long userId);

    Collection<ItemDto> getAllItems(Long userId);

    Collection<ItemDto> searchItems(String text);

    Comment addComment(Long itemId, Comment comment, Long userId);
}
