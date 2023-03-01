package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int idNumber = 0;

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

    public Film getById (int id) {
        return films.get(id);
    }

    public List<Film> getAllInList() {
        return new ArrayList<>(films.values());
    }

    public Map<Integer,Film> getAll() {
        return films;
    }
}
