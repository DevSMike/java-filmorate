package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long idNumber = 0;

    public void add (Film film) {
        film.setId(++idNumber);
        films.put(idNumber, film);
    }

    public void update (Film film) {
        films.put(film.getId(), film);
    }

    public void delete (Film film) {
        films.remove(film.getId());
    }

    public List<Film> getFilmsList() {
        return new ArrayList<>(films.values());
    }

    public Map<Long, Film> getFilmsMap() {
        return films;
    }

    public List<Film> getTopLikesFilms(int count) {
        return getFilmsList().stream()
                .sorted(Comparator.comparing(Film::getLikesLength).reversed()).limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Film getFilmById(long id) {
        return getFilmsMap().get(id);
    }

}
