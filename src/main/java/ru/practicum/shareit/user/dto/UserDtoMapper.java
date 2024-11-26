package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.User;

public class UserDtoMapper {
    public static UserDto transformToDto(User user) {
        return new UserDto(user.getName());
    }
}
