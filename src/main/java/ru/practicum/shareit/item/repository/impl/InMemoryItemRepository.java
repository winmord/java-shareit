package ru.practicum.shareit.item.repository.impl;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item createItem(Item item) {
        return items.put(item.getId(), item);
    }

    @Override
    public Item changeItem(Long itemId, Item item) {
        items.put(itemId, item);
        return items.get(itemId);
    }

    @Override
    public Item getItem(Long itemId) {
        return items.get(itemId);
    }

    @Override
    public Collection<Item> getAllItems() {
        return items.values();
    }

    @Override
    public Collection<Item> searchItems(String text) {
        return items.values().stream()
                .filter(item -> item.isAvailable()
                        && (item.getName().contains(text) || item.getDescription().contains(text))
                )
                .collect(Collectors.toList());
    }
}
