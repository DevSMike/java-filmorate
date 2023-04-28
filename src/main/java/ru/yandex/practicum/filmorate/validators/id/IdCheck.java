package ru.yandex.practicum.filmorate.validators.id;

public interface IdCheck {

    void checkFilmId(long id);

    void checkUserId(long id);

    void checkGenreId(int id);
}
