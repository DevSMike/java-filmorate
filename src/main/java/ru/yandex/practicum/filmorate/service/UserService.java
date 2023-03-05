package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public void addUser(User user) {
        userStorage.add(user);
    }

    public void updateUser(User user) {
        userStorage.update(user);
    }

    public void deleteUser(User user) {
        userStorage.delete(user);
    }

    public User getUserById(long id) {
        return userStorage.getUsersMap().get(id);
    }

    public List<User> getUsersInList() {
        return userStorage.getUsersList();
    }


    public void addFriend(long id, long friendId) {
        userStorage.getUsersMap().get(id).getFriends().add(friendId);
        userStorage.getUsersMap().get(friendId).getFriends().add(id);
    }

    public void deleteFriend(long id, long friendId) {
        userStorage.getUsersMap().get(id).getFriends().remove(friendId);
        userStorage.getUsersMap().get(friendId).getFriends().remove(id);
    }

    public List<User> getUserFriends(long id) {
       return userStorage.getUsersList().stream()
                .filter(x -> userStorage.getUsersMap().get(id).getFriends().contains(x.getId()))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(long id, long otherId) {
        return userStorage.getUsersList().stream().filter(x -> userStorage.getUsersMap().get(id).getFriends()
                        .contains(x.getId())).filter(x -> userStorage.getUsersMap().get(otherId).getFriends()
                .contains(x.getId())).collect(Collectors.toList());
    }

}
