package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ToServerBookingDto {
    private long itemId;
    private LocalDateTime start;
    private LocalDateTime end;
}
