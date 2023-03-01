package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

    void add (User user);

    void update (User user);

    void delete (User user);

}
