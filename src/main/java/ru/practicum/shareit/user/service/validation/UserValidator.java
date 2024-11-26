package ru.practicum.shareit.user.service.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidator {
    private final UserRepository userRepository;

    public void validateNewUser(User user) {
        validateName(user.getName());
        validateEmail(user.getEmail(), 0);
    }

    public void validatePatchedUser(User user) {
        long userId = user.getId();
        validateExists(userId);

        String userName = user.getName();
        if (userName != null) {
            validateName(user.getName());
        }

        String userEmail = user.getEmail();
        if (userEmail != null) {
            validateEmail(userEmail, userId);
        }
    }

    public void validateExists(long id) {
        throwExceptionIfTrue(userRepository.get(id) == null,
                String.format("User with id = %d not found.", id)
        );
    }

    private void validateName(String userName) {
        throwExceptionIfTrue(isStringEmptyInJson(userName),
                "Name can not be empty or contain only whitespaces."
        );
    }

    //Валидация почты  пользователя. Если userId = 0, то считается, что проверяется почта нового пользователя,
    // иначе - существующего
    private void validateEmail(String email, long userId) {
        //Проверка, что поле с почтой в запросе не пустое
        boolean condition1 = !isStringEmptyInJson(email);

        //Проверка, что почта соответствует шаблону
        String emailRegExp = "[\\w\\.]+@[a-z0-9]+\\.[a-z][a-z]+";
        boolean condition2 = email.matches(emailRegExp);

        //Проверка, что почта не занята
        boolean condition3;
        User userFoundByMail = userRepository.get(email);
        if (userId == 0) {
            condition3 = userFoundByMail == null;
        } else {
            condition3 = userFoundByMail == null || userFoundByMail.getId() == userId;
        }

        String errorMessage = "";
        if (!condition1) {
            errorMessage = "Email is empty or absent.";
        } else if (!condition2) {
            errorMessage = "Email doesn't match template.";
        } else if (!condition3) {
            errorMessage = "Email is busy";
        }
        throwExceptionIfTrue(!errorMessage.isBlank(), errorMessage);
    }

    private void throwExceptionIfTrue(boolean condition, String message) {
        if (condition) {
            ValidationException exc = new ValidationException(message);
            log.warn(message, exc);
            throw exc;
        }
    }

    private static boolean isStringEmptyInJson(String string) {
        return string == null || string.isBlank();
    }


}
