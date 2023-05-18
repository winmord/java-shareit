package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.request.ItemRequest;

public class ItemRequestMapper {
    private ItemRequestMapper() {
        throw new IllegalStateException("Mapper class");
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .requestor(itemRequest.getRequestor())
                .created(itemRequest.getCreated())
                .build();
    }

    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto) {
        return ItemRequest.builder()
                .id(itemRequestDto == null ? null : itemRequestDto.getId())
                .description(itemRequestDto == null ? null : itemRequestDto.getDescription())
                .requestor(itemRequestDto == null ? null : itemRequestDto.getRequestor())
                .created(itemRequestDto == null ? null : itemRequestDto.getCreated())
                .build();
    }
}
