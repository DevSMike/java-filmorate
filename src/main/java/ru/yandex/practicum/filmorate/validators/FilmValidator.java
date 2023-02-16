package ru.yandex.practicum.filmorate.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Map;

public class FilmValidator {

    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final LocalDate FIRST_MOVIE_RELEASE = LocalDate.of(1895, 12, 28);
    private static final Logger log = LoggerFactory.getLogger(FilmValidator.class);

    public static void validate (Film film, Map<Integer, Film> films, HttpMethod method) throws ValidationException {
        validateName(film);
        validateDescription(film);
        validateReleaseDate(film);
        validateMovieDuration(film);
        if (method.toString().equals("PUT")) {
            validateId(film, films);
        }
    }
    private static void validateId(Film film, Map<Integer, Film> films) {
        if (!films.containsKey(film.getId())) {
            log.debug(film + " failed validationId");
            throw new ValidationException("Film id is incorrect");
        }
        log.debug(film + " passed validationId");
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
