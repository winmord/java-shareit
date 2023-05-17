package ru.practicum.shareit.booking.model;

import ru.practicum.shareit.user.model.User;

public interface ShortBooking {
    Long getId();

    User getBooker();

    default Long getBookerId() {
        return getBooker().getId();
    }
}
