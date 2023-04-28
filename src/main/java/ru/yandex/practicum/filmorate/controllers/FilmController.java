package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.model.film.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final FilmService service;

    @PostMapping()
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("object " + film + " passed validation. return object");
        service.addFilm(film);
        return film;
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        service.updateFilm(film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        return service.getFilmsInList();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable long id) {
        return service.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeToFilm(@PathVariable long id, @PathVariable long userId) {
        service.addLikeToFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeToFilm(@PathVariable long id, @PathVariable long userId) {
        service.deleteFilmLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count,
                                      @RequestParam(defaultValue = "0") int genreId,
                                      @RequestParam(defaultValue = "0") int year) {
        return service.getTopLikesFilms(count, genreId, year);
    }

    @DeleteMapping("/{filmId}")
    public void deleteFilm(@PathVariable long filmId) {
        service.deleteFilm(filmId);
    }
}
