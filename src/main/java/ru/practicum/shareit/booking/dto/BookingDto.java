package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class BookingDto {
    private final Long id;

    @NotNull
    @FutureOrPresent
    private final LocalDateTime start;

    @NotNull
    @FutureOrPresent
    private final LocalDateTime end;

    private final Long itemId;
    private final Item item;
    private final User booker;
    private final BookingStatus status;
    private final Long bookerId;
}
