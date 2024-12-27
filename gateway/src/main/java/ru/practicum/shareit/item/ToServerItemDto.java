package ru.practicum.shareit.item;

import lombok.Getter;
import ru.practicum.shareit.user.User;

@Getter
public class ToServerItemDto {
    private long id;

    private User owner;

    private String name;

    private String description;

    private Boolean available;

    private long requestId;
}
