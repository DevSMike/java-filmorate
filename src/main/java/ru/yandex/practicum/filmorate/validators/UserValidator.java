package ru.yandex.practicum.filmorate.validators;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class UserValidator {

    private final UserStorage userStorage ;

    private static final Logger log = LoggerFactory.getLogger(UserValidator.class);

    public static void validate (User user) {
        validateLogin(user);
        validateEmail(user);
        validateUsername(user);
        validateBirthDate(user);
    }



    public static void validateFriendsSet(User user, long friendId) {
        validateFriends(user, friendId);
    }

    private static void validateFriends(User user, long fId) {
        if (!user.getFriends().contains(fId)) {
            log.debug(user + " friend not found, failed validation");
            throw new NullPointerException("Friend not found");
        }
        log.debug(user + " have a friend, passed validation");
    }

    public void validateId (Long id) {
        if (!userStorage.getUsersMap().containsKey(id)) {
            log.debug(id + " failed validationId");
            throw new ValidationException("Id is incorrect");
        }
        log.debug(id + " passed validationId");
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
