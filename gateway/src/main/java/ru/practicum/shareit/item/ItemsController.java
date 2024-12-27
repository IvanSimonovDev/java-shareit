package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UsersClient;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
public class ItemsController {
    private final ItemsClient itemsClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestBody ToServerItemDto itemDto) {
        log.info("Started request handling by ItemController#createItem(...)");
        return itemsClient.createItem(userId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable long itemId) {
        log.info("Started request handling by ItemController#getItem(...)");
        return itemsClient.getItem(itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsOfUser(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Started request handling by ItemController#getItemsOfUser(...)");
        return itemsClient.getItemsOfUser(userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> patchItem(@PathVariable("itemId") String itemIdString,
                                                 @RequestHeader("X-Sharer-User-Id") long userId,
                                                 @RequestBody Item item
    ) {
        log.info("Started request handling by ItemController#patchItem(...)");
        return itemsClient.patchItem(itemIdString, userId, item);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchAvailableItems(@RequestParam String text) {
        log.info("Started request handling by ItemController#searchItems(...)");
        return itemsClient.searchAvailableItems(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@PathVariable("itemId") long itemId,
                                              @RequestHeader("X-Sharer-User-Id") long userId,
                                              @RequestBody Comment comment
    ) {
        log.info("Started request handling by ItemController#createComment(...)");
        return itemsClient.createComment(itemId, userId, comment);
    }
}
