package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.validation.UserValidator;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserValidator userValidator;
    private final UserRepository userRepository;

    public User createUser(User user) {
        userValidator.validateNewUser(user);
        return userRepository.create(user);
    }

    public User getUser(long id) {
        userValidator.validateExists(id);
        return userRepository.get(id);
    }

    public User updateUser(User user) {
        User newVersionOfUser;
        if (user.getId() == 0) {
            userValidator.validateUpdatedUserWithoutId(user);
            newVersionOfUser = user.withId(userRepository.get(user.getEmail()).getId());
        } else {
            userValidator.validateUpdatedUserWithId(user);
            newVersionOfUser = user;
        }
        return userRepository.update(newVersionOfUser);
    }

    public void deleteUser(long id) {
        userValidator.validateExists(id);
        userRepository.remove(id);
    }
}
