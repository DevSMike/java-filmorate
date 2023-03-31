package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.List;

public interface MpaStorage {

    List<Mpa> getAllMpa();
    Mpa getMpaById(int id);
}
