package ru.yandex.practicum.filmorate.storage.genres;

import ru.yandex.practicum.filmorate.model.film.Genres;

import java.util.List;

public interface GenresStorage {

    List<Genres> getAllGenres();
    Genres getGenreById(int id);
}
