package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, Long userId);

    ItemDto changeItem(Long itemId, ItemDto itemDto);

    ItemDto getItem(Long itemId);

    Collection<ItemDto> getAllItems();

    Collection<ItemDto> searchItems(String text);
}
