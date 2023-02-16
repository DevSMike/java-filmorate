package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dao.UserRepository;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository = new UserRepository();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping()
    public User addUser(@RequestBody User user) {
        UserValidator.validate(user, repository.getAll(), HttpMethod.POST);
        log.info("object " + user + " passed validation. returns object");
        repository.add(user);
        return user;
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) {
        UserValidator.validate(user, repository.getAll(), HttpMethod.PUT);
        log.info("object " + user + " passed validation. update and returns object");
        repository.update(user);
        return user;
    }

    @GetMapping()
    public List<User> getUsers() {
        return repository.getAllInList();
    }
}
