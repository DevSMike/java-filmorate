package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.Genres;
import ru.yandex.practicum.filmorate.storage.genres.GenresStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenresService {

    private final GenresStorage genresStorage;

    public List<Genres> getAllGenres() {
        return genresStorage.getAllGenres();
    }

    public Genres getGenreById(int id) {
        if (genresStorage.getAllGenres().stream().collect(Collectors.toMap(Genres::getId, x -> x)).containsKey(id)) {
            return genresStorage.getGenreById(id);
        } else throw new NullPointerException("Object not found!");
    }
}
