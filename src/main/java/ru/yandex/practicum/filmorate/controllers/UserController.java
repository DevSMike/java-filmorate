package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.Valid;
import javax.validation.ValidationException;
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
    public User updateUser(@Valid @RequestBody User user) {
        try {
            service.updateUser(user);
            return user;
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    @GetMapping()
    public List<User> getUsers() {
        return service.getUsersInList();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        try {
            return service.getUserById(id);
        } catch (ValidationException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriendToUser(@PathVariable long id, @PathVariable long friendId) {
        try {
            service.addFriend(id, friendId);
        } catch (ValidationException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        try {
            service.deleteFriend(id, friendId);
        } catch (ValidationException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable long id) {
        try {
            return service.getUserFriends(id);
        } catch (ValidationException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getUserCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        try {
            return service.getCommonFriends(id, otherId);
        } catch (ValidationException e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}
