package ru.yandex.practicum.filmorate.validators.id;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Genres;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genres.GenresStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntityIdValidator implements IdCheck {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenresStorage genresStorage;

    @Override
    public void checkFilmId(long id) {
        if (!filmStorage.getFilmsMap().containsKey(id)) {
            log.debug(id + " failed to find id");
            throw new NullPointerException("Film id is not found");
        }
        if (id < 0) {
            log.debug(id + " Film is incorrect");
            throw new ValidationException("Film id is incorrect");
        }
        log.debug(id + " passed validationId");
    }

    @Override
    public void checkUserId(long id) {
        if (!userStorage.getUsersMap().containsKey(id)) {
            log.debug(id + " failed to find id");
            throw new NullPointerException("User id is not found");
        }
        if (id <= 0) {
            log.debug(id + " User is incorrect");
            throw new ValidationException("User id is incorrect");
        }
        log.debug(id + " passed validationId");
    }

    @Override
    public void checkGenreId(int id) {
        if (!genresStorage.getAllGenres().stream().collect(Collectors.toMap(Genres::getId, x -> x)).containsKey(id)) {
            log.debug(id + "failed to find genre id");
            throw new NullPointerException("Genre id is not found");
        }
        if (id < 0) {
            throw new ValidationException(id + " Genre id is incorrect");
        }
        log.debug(id + " passed validationId");
    }
}
