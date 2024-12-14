package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public class ItemDtoMapper {
    public static FromServerItemDto transformToDto(Item item) {
        return new FromServerItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
    }

    public static List<FromServerItemDto> transformToDto(List<Item> itemsList) {
        return itemsList.stream().map(ItemDtoMapper::transformToDto).toList();
    }

    public static ItemDtoInFromServerBookingDto transformToNestedDto(Item item) {
        return new ItemDtoInFromServerBookingDto(item.getId(), item.getName());
    }
}
