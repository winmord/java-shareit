package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository {
    Item createItem(Item item);

    Item changeItem(Long itemId, Item item);

    Item getItem(Long itemId);

    Collection<Item> getAllItems();

    Collection<Item> searchItems(String text);
}
