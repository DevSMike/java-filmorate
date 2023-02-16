package ru.yandex.practicum.filmorate.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UserValidator {

    private static final Logger log = LoggerFactory.getLogger(UserValidator.class);

    public static void validate (User user, Map<Integer, User> users, HttpMethod method) {
        validateLogin(user);
        validateEmail(user);
        validateUsername(user);
        validateBirthDate(user);
        if (method.toString().equals("PUT")) {
            validateId(user, users);
        }
    }

    private static void validateId (User user, Map<Integer, User> users) {
        if (!users.containsKey(user.getId())) {
            log.debug(user + " failed validationId");
            throw new ValidationException("Id is incorrect");
        }
        log.debug(user + " passed validationId");
    }

    private static void validateEmail(User user) {
        if (!user.getEmail().contains("@") && !user.getEmail().isBlank()) {
            log.debug(user + " failed validationEmail");
            throw new ValidationException("Email is incorrect");
        }
        log.debug(user + " passed validationEmail");
    }

    private static void validateLogin(User user) {
        if (user.getLogin().contains(" ")) {
            log.debug(user + " failed validationLogin");
            throw new ValidationException("Login is incorrect");
        }
        log.debug(user + " passed validationLogin");
    }

    private static void validateUsername(User user) {

        try {
            if (user.getName().isEmpty()) {
                log.debug(user + " failed validationUsername");
                user.setName(user.getLogin());
            }
        } catch (NullPointerException e) {
            log.debug(user + " name is null");
            user.setName(user.getLogin());
        }
        log.debug(user + " passed validationUsername");
    }

    private static void validateBirthDate(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug(user + " failed validationBirthDate");
            throw new ValidationException("BirthDate is incorrect");
        }
        log.debug(user + " passed validationBirthDate");
    }

}
