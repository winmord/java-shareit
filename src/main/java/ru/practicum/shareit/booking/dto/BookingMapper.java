package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;

public class BookingMapper {
    private BookingMapper() {
        throw new IllegalStateException("Mapper class");
    }

    public static BookingDto toBookingDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(new BookingDto.Item(booking.getItem().getId(), booking.getItem().getName()))
                .booker(new BookingDto.User(booking.getBooker().getId(), booking.getBooker().getName()))
                .status(booking.getStatus())
                .build();
    }

    public static Booking toBooking(BookingShortDto bookingShortDto) {
        return Booking.builder()
                .start(bookingShortDto.getStart())
                .end(bookingShortDto.getEnd())
                .build();
    }
}