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

    public User patchUser(User user) {
        userValidator.validatePatchedUser(user);

        long userId = user.getId();
        User userFromStorage = userRepository.get(userId);
        if (user.getEmail() == null) {
            user.setEmail(userFromStorage.getEmail());
        }
        if (user.getName() == null) {
            user.setName(userFromStorage.getName());
        }

        return userRepository.update(user);
    }

    public void deleteUser(long id) {
        userValidator.validateExists(id);
        userRepository.remove(id);
    }
}
