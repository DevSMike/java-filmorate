package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
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

}
