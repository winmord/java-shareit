package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    private ItemMapper() {
        throw new IllegalStateException("Mapper class");
    }

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .owner(item.getOwner())
                .available(item.getAvailable())
                .request(item.getRequest())
                .lastBooking(item.getLastBooking())
                .nextBooking(item.getNextBooking())
                .comments(item.getComments())
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
                .comments(itemDto.getComments())
                .build();
    }
}
