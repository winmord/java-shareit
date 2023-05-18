package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.ShortBooking;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Collection<Booking> findAllByBookerId(Long bookerId, Sort sort);

    Collection<Booking> findAllByBookerIdAndStartAfter(Long bookerId, LocalDateTime date, Sort sort);

    Collection<Booking> findAllByBookerIdAndStartBeforeAndEndAfter(Long bookerId, LocalDateTime date1, LocalDateTime date2, Sort sort);

    Collection<Booking> findAllByBookerIdAndEndBefore(Long bookerId, LocalDateTime date, Sort sort);

    Collection<Booking> findAllByBookerIdAndStatus(Long bookerId, BookingStatus status, Sort sort);

    Collection<Booking> findAllByItemOwnerId(Long bookerId, Sort sort);

    Collection<Booking> findAllByItemOwnerIdAndStartAfter(Long bookerId, LocalDateTime date, Sort sort);

    Collection<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfter(Long bookerId, LocalDateTime date1, LocalDateTime date2, Sort sort);

    Collection<Booking> findAllByItemOwnerIdAndEndBefore(Long bookerId, LocalDateTime date, Sort sort);

    Collection<Booking> findAllByItemOwnerIdAndStatus(Long bookerId, BookingStatus status, Sort sort);

    ShortBooking findFirstByItemIdAndStartBefore(Long itemId, LocalDateTime date, Sort sort);

    ShortBooking findFirstByItemIdAndStartAfterAndStatusNot(Long itemId, LocalDateTime date, BookingStatus status, Sort sort);

    Collection<Booking> findAllByBookerIdAndItemIdAndEndBefore(Long bookerId, Long itemId, LocalDateTime date);
}
