package ru.yandex.practicum.filmorate.validators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.validators.old.FilmValidator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.Assert.assertEquals;

public class FilmValidatorTest {

    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    @Test
    public void shouldBeFilmWhenDataIsCorrect() {
        Film film = Film.builder()
                .name("Avatar")
                .description("Cool film")
                .releaseDate(LocalDate.of(2005, 3, 25))
                .duration(250)
                .build();

        assertDoesNotThrow(() -> FilmValidator.validate(film), "Exception throws");
    }

    @Test
    public void shouldThrowExceptionWhileEmptyName() {
        Film film = Film.builder()
                .name("")
                .description("Cool film")
                .releaseDate(LocalDate.of(2005, 3, 25))
                .duration(-250)
                .build();
        ValidationException e = assertThrows(ValidationException.class, () -> FilmValidator.validate(film),
                "Exceptions does not throw");
        assertEquals(e.getMessage(), "Film name is Empty", "Messages are different");
    }

    @Test
    public void shouldThrowExceptionWhileBiggerDescription() {
        Film film = Film.builder()
                .name("Avatar")
                .description("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль." +
                        "Здесь они хотят разыскать господина Огюста Куглова," +
                        " который задолжал им деньги, а именно 20 миллионов." +
                        " о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.")
                .releaseDate(LocalDate.of(2005, 3, 25))
                .duration(250)
                .build();

        ValidationException e = assertThrows(ValidationException.class, () -> FilmValidator.validate(film));
        assertEquals(e.getMessage(), "Description is bigger than max", "Messages are different");
    }

    @Test
    public void shouldThrowExceptionWhileReleaseDateIsBeforeMin() {
        Film film = Film.builder()
                .name("Avatar")
                .description("Cool film")
                .releaseDate(LocalDate.of(1890, 3, 25))
                .duration(250)
                .build();
        ValidationException e = assertThrows(ValidationException.class, () -> FilmValidator.validate(film),
                "Exceptions does not throw");
        assertEquals(e.getMessage(), "Release date is before than first movie release",
                "Messages are different");
    }

    @Test
    public void shouldBeThrowExceptionWhileDurationIsNegative() {
        Film film = Film.builder()
                .name("Avatar")
                .description("Cool film")
                .releaseDate(LocalDate.of(2005, 3, 25))
                .duration(-250)
                .build();
        ValidationException e = assertThrows(ValidationException.class, () -> FilmValidator.validate(film),
                "Exceptions does not throw");
        assertEquals(e.getMessage(), "Movie's duration is negative", "Messages are different");
    }

    @Test
    public void shouldBeEqualsObjectsAfterUpdateIfValidated() {
        Film film = Film.builder()
                .name("Avatar")
                .description("Cool film")
                .releaseDate(LocalDate.of(2005, 3, 25))
                .duration(250)
                .build();
        Map<Long, Film> films = new HashMap<>();

        FilmValidator.validate(film);
        films.put(film.getId(), film);
        String json = "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Film Updated\",\n" +
                "  \"releaseDate\": \"1989-04-17\",\n" +
                "  \"description\": \"New film update decription\",\n" +
                "  \"duration\": 190,\n" +
                "  \"rate\": 4\n" +
                "}";

        Film film2 = gson.fromJson(json, Film.class);
        FilmValidator.validate(film2);

        films.put(film2.getId(), film2);
        assertEquals(film2.getName(), films.get(film2.getId()).getName(), "Objects are different");
    }

    @Test
    public void shouldBeExceptionAfterUnknownUpdate() {
        Film film = Film.builder()
                .name("Avatar")
                .description("Cool film")
                .releaseDate(LocalDate.of(2005, 3, 25))
                .duration(250)
                .build();
        Map<Long, Film> films = new HashMap<>();
        FilmValidator.validate(film);
        films.put(film.getId(), film);
        String json = "{\n" +
                "  \"id\": 9999,\n" +
                "  \"name\": \"Film Updated\",\n" +
                "  \"releaseDate\": \"1989-04-17\",\n" +
                "  \"description\": \"New film update decription\",\n" +
                "  \"duration\": 190,\n" +
                "  \"rate\": 4\n" +
                "}";
    }
}
