package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestShort;
import ru.practicum.shareit.request.service.RequestsService;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final RequestsService requestsService;

    @PostMapping
    public ItemRequestShort createRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                          @RequestBody ItemRequest request) {
        log.info("Started request handling by ItemRequestController#createRequest(...)");
        log.info("Started creating request with description {} for user(id = {})", request.getDescription(), userId);
        request.setCreatorId(userId);
        ItemRequestShort createdRequest = requestsService.createRequest(request);
        log.info("Request with id = {} created for user(id = {}).",
                createdRequest.getId(),
                createdRequest.getCreatorId()
        );
        return createdRequest;
    }

    @GetMapping
    public List<ItemRequestShort> getRequestsOfUser(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Started request handling by ItemRequestController#getRequestsOfUser(...)");
        log.info("Started getting requests for user(id = {})", userId);
        List<ItemRequestShort> foundRequests = requestsService.getRequestsOfUser(userId);
        log.info("Requests for user with id = {} found", userId);
        return foundRequests;
    }

    @GetMapping("/all")
    public List<ItemRequestShort> getRequestsNotFromUser(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Started request handling by ItemRequestController#getRequestsNotFromUser(...)");
        log.info("Started getting requests not from user(id = {})", userId);
        List<ItemRequestShort> foundRequests = requestsService.getRequestsOfOthers(userId);
        log.info("Requests not from user with id = {} found", userId);
        return foundRequests;
    }

    @GetMapping("/{requestId}")
    public ItemRequestShort getItemRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @PathVariable long requestId) {
        log.info("Started request handling by ItemRequestController#getItemRequest(...)");
        log.info("Started getting request(id = {}) for user with id = {}", requestId, userId);
        ItemRequestShort foundRequest = requestsService.getRequest(requestId);
        log.info("Request with id = {} found", requestId);
        return foundRequest;
    }
}

