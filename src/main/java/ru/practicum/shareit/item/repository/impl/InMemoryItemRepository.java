package ru.practicum.shareit.item.repository.impl;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private Long autoIncrementId = 1L;
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item createItem(Item item) {
        item.setId(autoIncrementId++);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item changeItem(Long itemId, Item item) {
        String itemName = item.getName();
        String itemDescription = item.getDescription();
        Boolean itemIsAvailable = item.getAvailable();

        Item repositoryItem = items.get(itemId);

        if (itemName != null && !Objects.equals(itemName, repositoryItem.getName())) {
            repositoryItem.setName(itemName);
        }

        if (itemDescription != null && !Objects.equals(itemDescription, repositoryItem.getDescription())) {
            repositoryItem.setDescription(itemDescription);
        }

        if (itemIsAvailable != null && !Objects.equals(itemIsAvailable, repositoryItem.getAvailable())) {
            repositoryItem.setAvailable(itemIsAvailable);
        }

        return repositoryItem;
    }

    @Override
    public Optional<Item> getItem(Long itemId) {
        return Optional.of(items.get(itemId));
    }

    @Override
    public Collection<Item> getAllItems(Long userId) {
        return items.values().stream()
                .filter(item -> Objects.equals(item.getOwner().getId(), userId))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Item> searchItems(String text) {
        String lowerText = text.toLowerCase();

        return items.values().stream()
                .filter(item -> item.getAvailable()
                        && (item.getName().toLowerCase().contains(lowerText)
                        || item.getDescription().toLowerCase().contains(lowerText))
                )
                .collect(Collectors.toList());
    }
}
