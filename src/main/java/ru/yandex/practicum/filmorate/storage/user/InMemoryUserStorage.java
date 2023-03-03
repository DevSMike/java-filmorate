package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long idNumber = 0;

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

    public List<User> getUsersList() {
        return new ArrayList<>(users.values());
    }

    public Map<Long, User> getUsersMap() {
        return users;
    }

}
