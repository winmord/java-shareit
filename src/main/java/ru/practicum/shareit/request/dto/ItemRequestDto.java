package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@Builder
public class ItemRequestDto {
    private final Long id;
    @NotNull
    @NotBlank
    private final String description;

    private final LocalDateTime created;

    private Collection<ItemDto> items;
}