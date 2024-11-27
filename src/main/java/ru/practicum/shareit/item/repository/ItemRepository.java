package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    Item create(Item item);

    Item update(Item item);

    Item get(long id);

    List<Item> getAll();

    List<Item> getAll(long userId);

    List<Item> getAllAvailable();

    List<Item> searchAvailableItems(String text);

    void remove(long id);
}