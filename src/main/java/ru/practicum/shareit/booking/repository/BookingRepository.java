package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.ShortBooking;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Collection<Booking> findAllByBookerIdOrderByStartDesc(Long bookerId);

    Collection<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Long bookerId, LocalDateTime date);

    Collection<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long bookerId, LocalDateTime date1, LocalDateTime date2);

    Collection<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(Long bookerId, LocalDateTime date);

    Collection<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Long bookerId, BookingStatus status);

    Collection<Booking> findAllByItemOwnerIdOrderByStartDesc(Long bookerId);

    Collection<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Long bookerId, LocalDateTime date);

    Collection<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Long bookerId, LocalDateTime date1, LocalDateTime date2);

    Collection<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Long bookerId, LocalDateTime date);

    Collection<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(Long bookerId, BookingStatus status);

    ShortBooking findFirstByItemIdAndStartBeforeOrderByStartDesc(Long itemId, LocalDateTime date);

    ShortBooking findFirstByItemIdAndStartAfterAndStatusNotOrderByStartAsc(Long itemId, LocalDateTime date, BookingStatus status);

    Collection<Booking> findAllByBookerIdAndItemIdAndEndBefore(Long bookerId, Long itemId, LocalDateTime date);
}
