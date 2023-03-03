package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import javax.validation.Valid;
import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmService service;
    private final UserService userService;

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        FilmValidator.validate(film);
        log.info("object " + film + " passed validation. return object");
        service.addFilm(film);
        return film;
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        FilmValidator.validatePutMethod(film, service.getFilmsInMap());
        log.info("object " + film + " passed validation. update and returns object");
        service.updateFilm(film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        return service.getFilmsInList();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable long id) {
        FilmValidator.validate(service.getFilmsInMap().get(id));
        return service.getFilmsInMap().get(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeToFilm(@PathVariable long id, @PathVariable long userId) {
        FilmValidator.validate(service.getFilmsInMap().get(id));
        UserValidator.validate(userService.getUsersInMap().get(userId));
        service.addLikeToFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeToFilm(@PathVariable long id, @PathVariable long userId) {
        FilmValidator.validate(service.getFilmsInMap().get(id));
        UserValidator.validate(userService.getUsersInMap().get(userId));
        service.deleteFilmLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return service.getTopLikesFilms(count);
    }
}
