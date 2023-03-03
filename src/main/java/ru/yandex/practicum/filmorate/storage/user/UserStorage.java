package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {

    void add (User user);

    void update (User user);

    void delete (User user);

    List<User> getUsersList();

    Map<Long, User> getUsersMap();

}
