package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;

    public void addFilm(Film film) {
        filmStorage.add(film);
    }

    public void updateFilm(Film film) {
        filmStorage.update(film);
    }

    public Film getFilmById(long id) {
        return filmStorage.getFilmsMap().get(id);
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

    public void addLikeToFilm(long id, long userId) {
        filmStorage.getFilmsMap().get(id).getLikes().add(userId);
    }

    public List<Film> getTopLikesFilms(int count) {
        return filmStorage.getFilmsList().stream()
                .sorted(Comparator.comparing(Film::getLikesLength).reversed()).limit(count)
                .collect(Collectors.toList());
    }

    public void deleteFilmLike(long id, long userId) {
        filmStorage.getFilmsMap().get(id).getLikes().remove(userId);
    }
}
