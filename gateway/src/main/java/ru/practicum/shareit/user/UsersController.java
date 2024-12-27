package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UsersClient usersClient;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        log.info("Started request handling by UserController#createUser(...)");
        ResponseEntity<Object> answer = usersClient.createUser(user);
        return answer;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object>  getUser(@PathVariable long userId) {
        log.info("Started request handling by UserController#getUser(...)");
        ResponseEntity<Object> answer = usersClient.getUser(userId);
        return answer;
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object>  patchUser(@PathVariable("userId") String userIdString, @RequestBody User user) {
        log.info("Started request handling by UserController#patchUser(...)");
        long userId = Long.parseLong(userIdString);
        user.setId(userId);
        return usersClient.patchUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("Started request handling by UserController#deleteUser(...)");
        usersClient.deleteUser(userId);
    }
}
