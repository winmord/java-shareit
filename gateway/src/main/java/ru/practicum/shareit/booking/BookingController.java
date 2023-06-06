package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.error.ErrorResponse;

import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
@Validated
@Slf4j
public class BookingController {
    private final BookingClient bookingClient;

    @Autowired
    public BookingController(BookingClient bookingClient) {
        this.bookingClient = bookingClient;
    }

    @PostMapping
    public ResponseEntity<Object> createBooking(@Validated @RequestBody BookingShortDto bookingShortDto,
                                                @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingClient.createBooking(bookingShortDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@PathVariable Long bookingId,
                                                 @RequestParam(name = "approved") Boolean approved,
                                                 @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingClient.approveBooking(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@PathVariable Long bookingId,
                                             @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingClient.getBooking(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @RequestParam(name = "state", required = false, defaultValue = "ALL") BookingState state,
                                                 @RequestParam(name = "from", required = false, defaultValue = "0") Long from,
                                                 @RequestParam(name = "size", required = false, defaultValue = "20") Long size) {
        return bookingClient.getAllBookings(userId, state, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getOwnerBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                   @RequestParam(name = "state", required = false, defaultValue = "ALL") BookingState state,
                                                   @RequestParam(name = "from", required = false, defaultValue = "0") Long from,
                                                   @RequestParam(name = "size", required = false, defaultValue = "20") Long size) {
        return bookingClient.getOwnerBookings(userId, state, from, size);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationError(final MethodArgumentNotValidException e) {
        log.error("validation failed: {}", e.getMessage(), e);
        return new ErrorResponse(e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField)
                .collect(Collectors.toList())
                .toString());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse conversionError(final MethodArgumentTypeMismatchException e) {
        log.info("conversion error: {}", e.getMessage(), e);
        return new ErrorResponse("Unknown state: " + Objects.requireNonNull(e.getValue()));
    }
}