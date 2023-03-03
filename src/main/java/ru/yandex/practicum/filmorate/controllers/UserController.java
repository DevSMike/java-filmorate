package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    @PostMapping()
    public User addUser(@Valid  @RequestBody User user) {
        UserValidator.validate(user);
        log.info("object " + user + " passed validation. returns object");
        service.addUser(user);
        return user;
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        UserValidator.validatePutMethod(user, service.getUsersInList().stream()
                .collect(Collectors.toMap(User::getId, a->a)));
        log.info("object " + user + " passed validation. update and returns object");
        service.updateUser(user);
        return user;
    }

    @GetMapping()
    public List<User> getUsers() {
        return service.getUsersInList();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        UserValidator.validate(service.getUsersInMap().get(id));
        return service.getUsersInMap().get(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriendToUser(@PathVariable long id, @PathVariable long friendId) {
        UserValidator.validate(service.getUsersInMap().get(id));
        UserValidator.validate(service.getUsersInMap().get(id));
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        UserValidator.validatePutMethod(service.getUsersInMap().get(id), service.getUsersInMap());
        UserValidator.validateFriendsSet(service.getUsersInMap().get(id),friendId);
        service.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable long id) {
        UserValidator.validate(service.getUsersInMap().get(id));
        return service.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getUserCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        UserValidator.validatePutMethod(service.getUsersInMap().get(id), service.getUsersInMap());
        UserValidator.validatePutMethod(service.getUsersInMap().get(otherId), service.getUsersInMap());
        return service.getCommonFriends(id, otherId);
    }


}
