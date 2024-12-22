package ru.practicum.shareit.request.model;

import ru.practicum.shareit.item.repository.ItemInRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemRequestShort {
    long getId();

    long getCreatorId();

    String getDescription();

    LocalDateTime getCreated();

    List<ItemInRequest> getItems();
}
