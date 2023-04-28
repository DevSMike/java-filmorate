package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {

    void add(Film film);

    void update(Film film);

    void delete(Film film);

    List<Film> getFilmsList();

    Map<Long, Film> getFilmsMap();

    List<Film> getTopLikesFilms(int count, int genreId, int year);

    Film getFilmById(long id);
}
