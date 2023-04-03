package ru.yandex.practicum.filmorate.validators.update;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

@Slf4j
@RequiredArgsConstructor
public class UpdateValidator implements ConstraintValidator<Update, Object> {

    final UserStorage userStorage;
    final FilmStorage filmStorage;
    String type;

    @Override
    public void initialize(Update annotation) {
        this.type = annotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        switch (type) {
            case "film": {
                Film film = (Film) o;
                if (filmStorage.getFilmsMap().containsKey(film.getId())) {
                    return true;
                } else {
                    throw new ValidationException("Film's id is incorrect");
                }
            }
            case "user": {
                User user = (User) o;
                if (userStorage.getUsersMap().containsKey(user.getId())) {
                    return true;
                } else {
                    throw new ValidationException("User's id is incorrect");
                }
            }
            default: {
                log.debug(type + " not supported!");
            }
        }
        return false;
    }
}

