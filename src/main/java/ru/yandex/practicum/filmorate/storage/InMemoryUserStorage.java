package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int idNumber = 0;

    public void add (User user) {
        user.setId(++idNumber);
        users.put(idNumber, user);
    }

    public void update (User user) {
        users.put(user.getId(), user);
    }

    public void delete (User user) {
        users.remove(user.getId());
    }

    public User getById (int id) {
        return users.get(id);
    }

    public List<User> getAllInList() {
        return new ArrayList<>(users.values());
    }

    public Map<Integer,User> getAll() {
        return users;
    }
}
