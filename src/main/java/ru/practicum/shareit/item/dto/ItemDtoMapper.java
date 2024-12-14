package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingShort;
import ru.practicum.shareit.booking.repository.BookingsRepository;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class ItemDtoMapper {
    private final BookingsRepository bookingsRepository;

    public NoBookingsFromServerItemDto transformToDto(Item item) {
        return new NoBookingsFromServerItemDto(item.getId(), item.getName(), item.getDescription(),
                item.getAvailable());
    }

    public List<NoBookingsFromServerItemDto> transformToNoBookingsDto(List<Item> itemsList) {
        return itemsList.stream().map(this::transformToDto).toList();
    }

    public List<WithBookingsFromServerItemDto> transformToWithBookingsDto(List<Item> itemsList) {
        Map<Long, WithBookingsFromServerItemDto> mapWithDtos = new HashMap<>();
        itemsList.forEach(item -> mapWithDtos.put(item.getId(), transformToWithEmptyBookingsDto(item)));

        Consumer<BookingShort> setLastBooking =
                bookingShort -> {
                    mapWithDtos.get(bookingShort.getItemId()).setLastBooking(bookingShort);
                };
        List<BookingShort> lastBookingsOfItems = bookingsRepository.lastBookingsOfItems(mapWithDtos.keySet(),
                LocalDateTime.now());
        lastBookingsOfItems.forEach(setLastBooking);

        Consumer<BookingShort> setNextFutureBooking = bookingShort ->
        {
            mapWithDtos.get(bookingShort.getItemId()).setNextBooking(bookingShort);
        };
        List<BookingShort> nBookingsOfItems = bookingsRepository.nearestFutureBookingsOfItems(mapWithDtos.keySet(),
                LocalDateTime.now());
        nBookingsOfItems.forEach(setNextFutureBooking);

        return new ArrayList<>(mapWithDtos.values());
    }

    public ItemDtoInFromServerBookingDto transformToNestedDto(Item item) {
        return new ItemDtoInFromServerBookingDto(item.getId(), item.getName());
    }

    private WithBookingsFromServerItemDto transformToWithEmptyBookingsDto(Item item) {
        return new WithBookingsFromServerItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                null,
                null
        );
    }
}
