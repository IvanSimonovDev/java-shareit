package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestShort;
import ru.practicum.shareit.request.repository.RequestRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RequestsServiceImpl implements RequestsService {
    private final RequestRepository requestRepository;
    private final RequestsValidator requestsValidator;

    public ItemRequestShort createRequest(ItemRequest request) {
        requestsValidator.validateNew(request);
        ItemRequest requestToSave = new ItemRequest(request.getCreatorId(), request.getDescription());
        long createdRequestId = requestRepository.save(requestToSave).getId();
        return requestRepository.findByIdIn(List.of(createdRequestId)).getFirst();
    }

    public List<ItemRequestShort> getRequestsOfUser(long userId) {
        return requestRepository.findByCreatorIdOrderByCreatedDesc(userId);
    }

    public List<ItemRequestShort> getRequestsOfOthers(long userId) {
        return requestRepository.findByCreatorIdNotOrderByCreatedDesc(userId);
    }

    public ItemRequestShort getRequest(long id) {
        requestsValidator.validateExists(id);
        return requestRepository.findByIdIn(List.of(id)).getFirst();
    }
}
