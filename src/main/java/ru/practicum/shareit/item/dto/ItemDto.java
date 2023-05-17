package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.ShortBooking;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validation.ValidationGroups;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
@Builder
public class ItemDto {
    private final Long id;

    @NotNull(groups = ValidationGroups.Create.class)
    @NotBlank(groups = ValidationGroups.Create.class)
    private final String name;

    @NotNull(groups = ValidationGroups.Create.class)
    @NotBlank(groups = ValidationGroups.Create.class)
    private final String description;

    @NotNull(groups = ValidationGroups.Create.class)
    private final Boolean available;

    private final User owner;
    private final ItemRequest request;

    private final ShortBooking lastBooking;
    private final ShortBooking nextBooking;

    private final Collection<CommentDto> comments;
}
