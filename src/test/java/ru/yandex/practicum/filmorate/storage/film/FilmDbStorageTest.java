package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genres;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.storage.film.likes.FilmLikes;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmLikes filmLikes;
    private final JdbcTemplate jdbcTemplate;


    @Test
    void add() {
        LinkedHashSet<Genres> genres = new LinkedHashSet<>();
        genres.add(new Genres(1, "Комедия"));
        Film film = Film.builder()
                .name("Avatar")
                .description("film")
                .mpa(new Mpa(1, "G"))
                .releaseDate(LocalDate.parse("2008-11-11"))
                .duration(162)
                .genres(genres)
                .build();
        filmStorage.add(film);
        int filmId = jdbcTemplate.queryForObject("SELECT FILM_ID FROM  FILMS ORDER BY FILM_ID DESC LIMIT 1;",
                Integer.class);
        assertEquals(filmStorage.getFilmById(filmId).getName(), film.getName(), "names are diff");

    }

    @Test
    void update() {
        LinkedHashSet<Genres> genres = new LinkedHashSet<>();
        genres.add(new Genres(1, "Комедия"));
        Film film = Film.builder()
                .name("Avatar 2 ")
                .description("film")
                .mpa(new Mpa(1, "G"))
                .releaseDate(LocalDate.parse("2008-11-11"))
                .duration(162)
                .genres(genres)
                .build();
        filmStorage.add(film);
        long filmId = jdbcTemplate.queryForObject("SELECT FILM_ID FROM  FILMS ORDER BY FILM_ID DESC LIMIT 1;",
                Integer.class);
        Film filmUpdate = Film.builder()
                .id(filmId)
                .name("Avatar2")
                .description("film")
                .mpa(new Mpa(1, "G"))
                .releaseDate(LocalDate.parse("2008-11-11"))
                .duration(162)
                .genres(genres)
                .build();
        filmStorage.update(filmUpdate);
        assertEquals(filmStorage.getFilmById(filmId).getName(), filmUpdate.getName(), "names are diff");
    }

    @Test
    void delete() {
        LinkedHashSet<Genres> genres = new LinkedHashSet<>();
        genres.add(new Genres(1, "Комедия"));
        Film film = Film.builder()
                .name("Nova 2")
                .description("film")
                .mpa(new Mpa(1, "G"))
                .releaseDate(LocalDate.parse("2008-11-11"))
                .duration(162)
                .genres(genres)
                .build();

        filmStorage.add(film);
        int len = filmStorage.getFilmsList().size();
        filmStorage.delete(film);
        assertEquals(filmStorage.getFilmsList().size(), len - 1, "lens are diffrerent");
    }

    @Test
    void getFilmsList() {
        LinkedHashSet<Genres> genres = new LinkedHashSet<>();
        genres.add(new Genres(1, "Комедия"));
        Film film = Film.builder()
                .name("Nova 2")
                .description("film")
                .mpa(new Mpa(1, "G"))
                .releaseDate(LocalDate.parse("2008-11-11"))
                .duration(162)
                .genres(genres)
                .build();
        filmStorage.add(film);
        List<Film> films = filmStorage.getFilmsList();
        assertEquals(films.size(), filmStorage.getFilmsList().size(), "size are diff`");
    }

    @Test
    void getFilmsMap() {
        LinkedHashSet<Genres> genres = new LinkedHashSet<>();
        genres.add(new Genres(1, "Комедия"));
        Film film = Film.builder()
                .name("Nova 3")
                .description("film")
                .mpa(new Mpa(1, "G"))
                .releaseDate(LocalDate.parse("2008-11-11"))
                .duration(162)
                .genres(genres)
                .build();
        filmStorage.add(film);
        Map<Long, Film> films = filmStorage.getFilmsMap();
        assertEquals(films.size(), filmStorage.getFilmsMap().size(), "size are diff");
    }

    @Test
    void getTopLikesFilms() {
        User user = User.builder()
                .login("Login")
                .birthday(LocalDate.parse("1990-12-12"))
                .name("Name")
                .email("newMail@mail.ru")
                .build();
        userStorage.add(user);
        long userId = jdbcTemplate.queryForObject("SELECT USER_ID FROM  USERS ORDER BY USER_ID DESC LIMIT 1;"
                , Integer.class);
        LinkedHashSet<Genres> genres = new LinkedHashSet<>();
        genres.add(new Genres(1, "Комедия"));
        Film film = Film.builder()
                .name("Nova111")
                .description("film")
                .mpa(new Mpa(1, "G"))
                .releaseDate(LocalDate.parse("2008-11-11"))
                .duration(162)
                .genres(genres)
                .build();

        Film filmTopLikes = Film.builder()
                .name("NovaL")
                .description("film")
                .mpa(new Mpa(1, "G"))
                .releaseDate(LocalDate.parse("2008-11-11"))
                .duration(162)
                .genres(genres)
                .build();
        filmStorage.add(film);
        filmStorage.add(filmTopLikes);
        long filmId = jdbcTemplate.queryForObject("SELECT FILM_ID FROM  FILMS ORDER BY FILM_ID DESC LIMIT 1;",
                Integer.class);
        filmLikes.addLikeToFilm(filmId, userId);
        List<Film> topLikes = filmStorage.getTopLikesFilms(1);
        assertEquals(topLikes.get(0).getName(), filmTopLikes.getName(), "names are diff");
    }

    @Test
    void getFilmById() {
        LinkedHashSet<Genres> genres = new LinkedHashSet<>();
        genres.add(new Genres(1, "Комедия"));
        Film filmById = Film.builder()
                .name("NovaById")
                .description("film")
                .mpa(new Mpa(1, "G"))
                .releaseDate(LocalDate.parse("2008-11-11"))
                .duration(162)
                .genres(genres)
                .build();
        filmStorage.add(filmById);
        long filmId = jdbcTemplate.queryForObject("SELECT FILM_ID FROM  FILMS ORDER BY FILM_ID DESC LIMIT 1;",
                Integer.class);
        assertEquals(filmStorage.getFilmById(filmId).getName(), filmById.getName(), "names are diff");
    }
}