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
        validateEmail(user.getEmail());
    }

    public void validateUpdatedUserWithId(User user) {
        long userId = user.getId();
        validateExists(userId);
        validateName(user.getName());
        validateEmail(user.getEmail(), userId);
    }

    public void validateUpdatedUserWithoutId(User user) {
        validateExists(user.getEmail());
        validateName(user.getName());
    }

    public void validateExists(long id) {
        throwExceptionIfTrue(userRepository.get(id) == null,
                String.format("User with id = %d not found.", id)
        );
    }

    public void validateExists(String email) {
        throwExceptionIfTrue(userRepository.get(email) == null,
                String.format("User with email = %s not found.", email)
        );
    }

    private void validateName(String userName) {
        throwExceptionIfTrue(isStringEmptyInJson(userName),
                "Name can not be empty or contain only whitespaces."
        );
    }

    // Валидация почты нового пользователя
    private void validateEmail(String email) {
        //Проверка, что поле с почтой в запросе не пустое
        boolean condition1 = !isStringEmptyInJson(email);

        //Проверка, что почта соответствует шаблону
        boolean condition2 = secondEmailCondition(email);

        //Проверка, что почта не занята
        boolean condition3 = userRepository.get(email) == null;

        throwExceptionWhenEmailNotValid(condition1, condition2, condition3);
    }

    //Валидация почты существующего пользователя
    private void validateEmail(String email, long userId) {
        //Проверка, что поле с почтой в запросе не пустое
        boolean condition1 = !isStringEmptyInJson(email);

        //Проверка, что почта соответствует шаблону
        boolean condition2 = secondEmailCondition(email);

        //Проверка, что почта не занята
        User userFoundByMail = userRepository.get(email);
        boolean condition3 = userFoundByMail == null || userFoundByMail.getId() == userId;

        throwExceptionWhenEmailNotValid(condition1, condition2, condition3);
    }

    //Проверка, что почта соответствует шаблону
    private boolean secondEmailCondition(String email) {
        String emailRegExp = "[\\w\\.]+@[a-z0-9]+\\.[a-z][a-z]+";
        return email.matches(emailRegExp);
    }

    // В метод передаются результаты проверки 3 критериев допустимости email, и если хотя бы 1 проверка не прошла,
    // то выбрасывается исключение
    private void throwExceptionWhenEmailNotValid(boolean condition1, boolean condition2, boolean condition3) {
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
