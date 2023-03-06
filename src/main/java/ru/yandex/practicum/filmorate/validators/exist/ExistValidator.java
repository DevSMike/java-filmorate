package ru.yandex.practicum.filmorate.validators.exist;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
@RequiredArgsConstructor
public class ExistValidator implements ConstraintValidator<Exist, Object> {

    final UserStorage userStorage;
    final FilmStorage filmStorage;
    String type;

    @Override
    public void initialize(Exist annotation) {
        this.type = annotation.message(); //todo
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        switch (type) {
            case "film": {
                if (o instanceof Long) {
                    return isExist(null, filmStorage, null, null, ((Long) o));
                }
                if (o instanceof Film) {
                    return isExist(null, filmStorage, null, (Film)o, null);
                }
                break;
            }
            case "user": {
                if (o instanceof Long) {
                    return isExist(userStorage, null, null, null, (Long) o);
                }
                if (o instanceof User) {
                    return isExist(userStorage, null, (User) o, null, null);
                }
                break;
            }

            default: {
                    log.debug(type + " not supported!");
                }
            }
            return false;
        }

        private boolean isExist(UserStorage userStorage, FilmStorage filmStorage, User user, Film film, Long id) {
            if (userStorage != null) {
                if (user != null) {
                    if (userStorage.getUsersMap().containsKey(user.getId())) {
                        return true;
                    }
                } else if (id != null) {
                    if (userStorage.getUsersMap().containsKey(id)) {
                        return true;
                    }
                }
            }

            if (filmStorage != null) {
                if (film != null) {
                    if (filmStorage.getFilmsMap().containsKey(film.getId())) {
                        return true;
                    }
                } else if (id != null) {
                    if (filmStorage.getFilmsMap().containsKey(id)) {
                        return true;
                    }
                }
            }
            throw new NullPointerException("object not found!");
        }
    }
