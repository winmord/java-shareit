package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Optional;

public interface ItemRepository {
    Item createItem(Item item);

    Item changeItem(Long itemId, Item item);

    Optional<Item> getItem(Long itemId);

    Collection<Item> getAllItems(Long userId);

    Collection<Item> searchItems(String text);
}
