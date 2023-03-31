package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Genres;
import ru.yandex.practicum.filmorate.service.GenresService;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/genres")
public class GenresController {

    private final GenresService genresService;

    @GetMapping
    public List<Genres> getAllGenres() {
        return genresService.getAllGenres();
    }

    @GetMapping("/{id}")
    public Genres getGenreById(@PathVariable  long id) {
        try {
            return genresService.getGenreById((int)id);
        } catch (NullPointerException e) {
            throw new ValidationException(e.getMessage());
        }
    }
}
