package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;

public interface BookingsRepository extends JpaRepository<Booking, Long>, QuerydslPredicateExecutor<Booking> {

    @Query(value = """
            SELECT EXISTS (SELECT 1
                           FROM bookings
                           WHERE bookings.start_timestamp >= ?1 AND
                           bookings.end_timestamp <= ?1);
            """,
            nativeQuery = true)
    Boolean bookingExists(LocalDateTime startDate, LocalDateTime endDate);
}
