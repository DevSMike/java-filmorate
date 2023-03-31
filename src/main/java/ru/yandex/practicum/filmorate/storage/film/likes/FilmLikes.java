package ru.yandex.practicum.filmorate.storage.film.likes;

public interface FilmLikes {

    void addLikeToFilm(long id, long filmId);

    void deleteFilmLike(long id, long filmId);
}
