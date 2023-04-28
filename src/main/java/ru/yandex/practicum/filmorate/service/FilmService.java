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

    public void deleteFilm(long id) {
        filmStorage.delete(filmStorage.getFilmById(id));
    }

    public List<Film> getFilmsInList() {
        return filmStorage.getFilmsList();
    }

    public void addLikeToFilm(long filmId, long userId) {
        entityIdValidator.checkUserId(userId);
        entityIdValidator.checkFilmId(filmId);
        filmLikesStorage.addLikeToFilm(filmId, userId);
    }

    public List<Film> getTopLikesFilms(int count, int genreId, int year) {
        if (genreId != 0) {
            entityIdValidator.checkGenreId(genreId);
        }
        return filmStorage.getTopLikesFilms(count, genreId, year);
    }

    public void deleteFilmLike(long filmId, long userId) {
        entityIdValidator.checkUserId(userId);
        entityIdValidator.checkFilmId(filmId);
        filmLikesStorage.deleteFilmLike(filmId, userId);
    }
}
