package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    @PostMapping
    public UserDto createUser(@RequestBody User user) {
        log.info("Started creating user");
        User createdUser = userService.createUser(user);
        log.info("User with id = {} created.", createdUser.getId());
        return UserDtoMapper.transformToDto(createdUser);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable long userId) {
        log.info("Started extracting user with id = {}", userId);
        User requestedUser = userService.getUser(userId);
        log.info("User with id = {} extracted", userId);
        return UserDtoMapper.transformToDto(requestedUser);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable("userId") String userIdString, @RequestBody User user) {
        long userId = (userIdString == null || userIdString.equals("null")) ? 0 : Long.parseLong(userIdString);
        user.setId(userId);
        log.info("Started updating user with id = {} and email = {}", userId, user.getEmail());
        User updatedUser = userService.updateUser(user);
        log.info("Ended updating user with id = {} and email = {}", userId, user.getEmail());
        return UserDtoMapper.transformToDto(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("Started deleting user with id = {}", userId);
        userService.deleteUser(userId);
        log.info("Ended deleting user with id = {}", userId);
    }
}
