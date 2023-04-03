package ru.yandex.practicum.filmorate.storage.genres;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenresDbStorageTest {

    private final GenresDbStorage genresDbStorage;

    @Test
    void getAllGenres() {
        assertEquals(genresDbStorage.getAllGenres().size(), 6, "size are diff");
    }

    @Test
    void getGenreById() {
        assertEquals(genresDbStorage.getGenreById(1).getName(), "Комедия", "names are diff");
    }
}