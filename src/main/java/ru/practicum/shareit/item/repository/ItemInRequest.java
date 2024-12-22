package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.user.model.User;

public interface ItemInRequest {
    long getId();

    User getOwner();

    String getName();
}
