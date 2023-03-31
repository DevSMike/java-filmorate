package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
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

    public List<User> getUserFriends(long id) {
        return getUsersList().stream().filter(x -> getUsersMap().get(id).getFriends().contains(x.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(long id) {
        return getUsersMap().get(id);
    }

    public List<User> getCommonFriends(long id, long otherId) {
        return getUsersList().stream().filter(x -> getUsersMap().get(id).getFriends()
                .contains(x.getId())).filter(x -> getUsersMap().get(otherId).getFriends()
                .contains(x.getId())).collect(Collectors.toList());
    }

}
