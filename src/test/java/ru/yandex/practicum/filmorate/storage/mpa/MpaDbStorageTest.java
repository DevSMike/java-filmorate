package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDbStorageTest {

    private final MpaStorage mpaStorage;

    @Test
    void getAllMpa() {
        assertEquals(mpaStorage.getAllMpa().size(), 5, "size are diff");
    }

    @Test
    void getMpaById() {
        assertEquals(mpaStorage.getMpaById(1).getName(), "G", "names are diff");

    }
}