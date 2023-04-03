package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.likes.FilmLikes;
import ru.yandex.practicum.filmorate.validators.id.EntityIdValidator;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final FilmLikes filmLikesStorage;
    private final EntityIdValidator entityIdValidator;

    public void addFilm(Film film) {
        filmStorage.add(film);
    }

    public void updateFilm(Film film) {
        entityIdValidator.checkFilmId(film.getId());
        filmStorage.update(film);
    }

    public Film getFilmById(long id) {
        entityIdValidator.checkFilmId(id);
        return filmStorage.getFilmById(id);
    }

    public void deleteFilm(Film film) {
        filmStorage.delete(film);
    }

    public List<Film> getFilmsInList() {
        return filmStorage.getFilmsList();
    }

    public Map<Long, Film> getFilmsInMap() {
        return filmStorage.getFilmsMap();
    }

    public void addLikeToFilm(long filmId, long userId) {
        entityIdValidator.checkUserId(userId);
        entityIdValidator.checkFilmId(filmId);
        filmLikesStorage.addLikeToFilm(filmId, userId);
    }

    public List<Film> getTopLikesFilms(int count) {
        if (filmStorage.getTopLikesFilms(count).isEmpty()) {
            return filmStorage.getFilmsList();
        }
        return filmStorage.getTopLikesFilms(count);
    }

    public void deleteFilmLike(long filmId, long userId) {
        entityIdValidator.checkUserId(userId);
        entityIdValidator.checkFilmId(filmId);
        filmLikesStorage.deleteFilmLike(filmId, userId);
    }
}
