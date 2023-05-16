package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.Collection;

public interface BookingService {
    public BookingDto createBooking(BookingDto bookingDto, Long userId);

    public BookingDto approveBooking(Long bookingId, Boolean approved, Long userId);

    public BookingDto getBooking(Long bookingId, Long userId);

    public Collection<BookingDto> getAllBookings(Long userId, BookingStatus status);

    public Collection<BookingDto> getOwnerBookings(Long userId, BookingStatus status);
}
