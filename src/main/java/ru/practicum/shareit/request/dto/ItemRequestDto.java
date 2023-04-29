package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ItemRequestDto {
    private final Long id;
    private final String description;
    private final Long requestor;
    private final LocalDateTime created;
}