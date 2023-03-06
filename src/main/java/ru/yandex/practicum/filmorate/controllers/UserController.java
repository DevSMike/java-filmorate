package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.model.User;;
import ru.yandex.practicum.filmorate.validators.exist.Exist;
import ru.yandex.practicum.filmorate.validators.update.Update;
import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        log.info("object " + user + " passed validation. returns object");
        service.addUser(user);
        return user;
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody @Update(message = "user") User user) {
        log.info("object " + user + " passed validation. update and returns object");
        service.updateUser(user);
        return user;
    }

    @GetMapping()
    public List<User> getUsers() {
        return service.getUsersInList();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable @Exist(message = "user") long id) {
        return service.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriendToUser(@PathVariable @Exist(message = "user") long id
            ,@PathVariable @Exist(message = "user") long friendId) {
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable @Exist(message = "user") long id
            ,@PathVariable @Exist(message = "user") long friendId) {
        service.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable @Exist(message = "user") long id) {
        return service.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getUserCommonFriends(@PathVariable @Exist(message = "user") long id,
                                           @PathVariable @Exist(message = "user") long otherId) {
        return service.getCommonFriends(id, otherId);
    }
}
