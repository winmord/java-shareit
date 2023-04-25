package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.request.ItemRequest;

public class ItemRequestMapper {
    private ItemRequestMapper() {
        throw new IllegalStateException("Mapper class");
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return ItemRequestDto.builder()
                .description(itemRequest.getDescription())
                .requestor(itemRequest.getRequestor() != null ? itemRequest.getRequestor().getId() : null)
                .created(itemRequest.getCreated())
                .build();
    }
}
