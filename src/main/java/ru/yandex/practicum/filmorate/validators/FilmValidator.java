package ru.yandex.practicum.filmorate.validators;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class FilmValidator {

    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final LocalDate FIRST_MOVIE_RELEASE = LocalDate.of(1895, 12, 28);
    private static final Logger log = LoggerFactory.getLogger(FilmValidator.class);
    private final FilmStorage filmStorage;

    public static void validate(Film film) throws ValidationException {
        validateName(film);
        validateDescription(film);
        validateReleaseDate(film);
        validateMovieDuration(film);
    }

    public void validateId(long id) {
        if (!filmStorage.getFilmsMap().containsKey(id)) {
            log.debug(id + " failed validationId");
            throw new ValidationException("Film id is incorrect");
        }
        log.debug(id + " passed validationId");
    }

    private static void validateName(Film film) {
        if (film.getName().isBlank()) {
            log.debug(film + " failed validationName");
            throw new ValidationException("Film name is Empty");
        }
        log.debug(film + " passed validationName");
    }

    private static void validateDescription(Film film) {
        if (film.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            log.debug(film + " failed validationDescription");
            throw new ValidationException("Description is bigger than max");
        }
        log.debug(film + " passed validationDescription");
    }

    private static void validateReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(FIRST_MOVIE_RELEASE)) {
            log.debug(film + " failed validationReleaseDate");
            throw new ValidationException("Release date is before than first movie release");
        }
        log.debug(film + " passed validationReleaseDate");
    }

    private static void validateMovieDuration(Film film) {
        if (film.getDuration() < 0) {
            log.debug(film + " failed validationMovieDuration");
            throw new ValidationException("Movie's duration is negative");
        }
        log.debug(film + " passed validationMovieDuration");
    }
}
