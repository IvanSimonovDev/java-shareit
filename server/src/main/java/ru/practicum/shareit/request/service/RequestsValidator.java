package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.service.validation.NotFoundException;
import ru.practicum.shareit.user.service.validation.RequestParamIncorrectOrAbsentException;
import ru.practicum.shareit.user.service.validation.UserValidator;

@RequiredArgsConstructor
@Component
public class RequestsValidator {
    private final UserValidator userValidator;
    private final RequestRepository requestsRepository;

    public void validateNew(ItemRequest request) {
        validateCreator(request.getCreatorId());
        validateDescription(request.getDescription());
    }

    public void validateExists(long requestId) {
        String excMessage = String.format("Request with id = %d not found", requestId);
        requestsRepository.findById(requestId).orElseThrow(() -> new NotFoundException(excMessage));
    }

    private void validateCreator(long creatorId) {
        userValidator.validateExists(creatorId);
    }

    private void validateDescription(String description) {
        String errorMsg = "Description can not be empty or contain only whitespaces.";
        RequestParamIncorrectOrAbsentException exc = new RequestParamIncorrectOrAbsentException(errorMsg);
        UserValidator.throwExceptionIfTrue(UserValidator.isStringEmptyInJson(description), exc);
    }

}