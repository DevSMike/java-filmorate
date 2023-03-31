package ru.yandex.practicum.filmorate.validators.exist;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.film.Genres;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.service.GenresService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ExistValidator implements ConstraintValidator<Exist, Object> {

    final UserStorage userStorage;
    final FilmStorage filmStorage;
    final MpaStorage mpaStorage;
    final GenresService genresService;
    String type;

    @Override
    public void initialize(Exist annotation) {
        this.type = annotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        switch (type) {
            case "film": {
                if (o instanceof Long) {
                    return isExist(null, filmStorage, null, null, ((Long) o), null, null);
                }
                if (o instanceof Film) {
                    return isExist(null, filmStorage, null, (Film)o, null, null, null);
                }
                break;
            }
            case "user": {
                if (o instanceof Long) {
                    return isExist(userStorage, null, null, null, (Long) o, null, null);
                }
                if (o instanceof User) {
                    return isExist(userStorage, null, (User) o, null, null, null, null);
                }
                break;
            }
            case "mpa": {
                if (o instanceof Long)
                    return isExist(null, null, null, null, (Long)o, mpaStorage, null);
                break;
            }

            case "genres": {
                if (o instanceof Long)
                    return isExist(null, null, null, null, (Long)o, null, genresService);
                break;
            }
            default: {
                    log.debug(type + " not supported!");
                }
            }
            return false;
        }

        private boolean isExist(UserStorage userStorage, FilmStorage filmStorage, User user, Film film, Long id,
                                MpaStorage mpa, GenresService genres) {
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
            if (mpa != null) {
                if (mpa.getAllMpa().stream().collect(Collectors.toMap(Mpa::getId, m->m)).containsKey((int)(long)id))
                    return true;
            }

            if (genres != null) {
                return genres.getAllGenres().stream().collect(Collectors.toMap(Genres::getId, g -> g))
                        .containsKey((int) (long) id);
            }

            return false;
        }
    }
