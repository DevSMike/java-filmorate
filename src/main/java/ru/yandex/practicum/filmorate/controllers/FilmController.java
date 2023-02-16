package ru.yandex.practicum.filmorate.controllers;

import org.springframework.http.HttpMethod;
import ru.yandex.practicum.filmorate.dao.FilmRepository;
import ru.yandex.practicum.filmorate.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.util.List;


@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmRepository repository = new FilmRepository();
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @PostMapping()
    public Film addFilm(@RequestBody Film film) {
        FilmValidator.validate(film, repository.getAll(), HttpMethod.POST);
        log.info("object " + film + " passed validation. return object");
        repository.add(film);
        return film;
    }

    @PutMapping()
    public Film updateFilm(@RequestBody Film film) {
        FilmValidator.validate(film, repository.getAll(), HttpMethod.PUT);
        log.info("object " + film + " passed validation. update and returns object");
        repository.update(film);
        return film;

    }

    @GetMapping
    public List<Film> getFilms() {
        return repository.getAllInList();
    }
}
