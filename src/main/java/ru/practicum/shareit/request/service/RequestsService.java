package ru.practicum.shareit.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestShort;

import java.util.List;

@Service
public interface RequestsService {
    ItemRequestShort createRequest(ItemRequest request);

    List<ItemRequestShort> getRequestsOfUser(long userId);

    List<ItemRequestShort> getRequestsOfOthers(long userId);

    ItemRequestShort getRequest(long id);
}
