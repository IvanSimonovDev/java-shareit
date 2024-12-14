package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.dto.BookingShort;

@Getter
@Setter
@AllArgsConstructor
public class WithBookingsFromServerItemDto {
    private long id;
    private String name;
    private String description;
    private boolean available;
    private BookingShort lastBooking;
    private BookingShort nextBooking;
}