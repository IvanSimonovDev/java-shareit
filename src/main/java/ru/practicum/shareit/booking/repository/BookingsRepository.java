package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.shareit.booking.dto.BookingShort;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface BookingsRepository extends JpaRepository<Booking, Long>, QuerydslPredicateExecutor<Booking> {

    @Query(value = """
            SELECT EXISTS (SELECT 1
                           FROM bookings
                           WHERE bookings.start_timestamp >= ?1 AND
                           bookings.end_timestamp <= ?1);
            """,
            nativeQuery = true)
    Boolean bookingExists(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = """
            SELECT MAX(start_timestamp) AS start,
                   MAX(end_timestamp) AS end,
                   item_id
            FROM bookings
            WHERE (item_id IN ?1)
            AND (NOT start_timestamp > ?2)
            GROUP BY item_id;
            """,
            nativeQuery = true)
    List<BookingShort> lastBookingsOfItems(Collection<Long> itemIds, LocalDateTime now);

    @Query(value = """
            SELECT MIN(start_timestamp) AS start,
                   MIN(end_timestamp) AS end,
                   item_id
            FROM bookings
            WHERE (item_id IN ?1)
            AND (start_timestamp > ?2)
            GROUP BY item_id;
            """,
            nativeQuery = true)
    List<BookingShort> nearestFutureBookingsOfItems(Collection<Long> itemIds, LocalDateTime now);
}
