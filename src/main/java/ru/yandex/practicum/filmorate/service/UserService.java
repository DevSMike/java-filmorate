package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.storage.user.friends.UserFriends;
import ru.yandex.practicum.filmorate.validators.id.EntityIdValidator;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final UserFriends userFriendsStorage;
    private final EntityIdValidator entityIdValidator;

    public void addUser(User user) {
        userStorage.add(user);
    }

    public void updateUser(User user) {
        entityIdValidator.checkUserId(user.getId());
        userStorage.update(user);
    }

    public void deleteUser(long id) {
        entityIdValidator.checkUserId(id);
        userStorage.delete(userStorage.getUserById(id));
    }

    public User getUserById(long id) {
        entityIdValidator.checkUserId(id);
        return userStorage.getUserById(id);
    }

    public List<User> getUsersInList() {
        return userStorage.getUsersList();
    }

    public void addFriend(long id, long friendId) {
        entityIdValidator.checkUserId(id);
        entityIdValidator.checkUserId(friendId);
        userFriendsStorage.addFriend(id, friendId);
    }

    public void deleteFriend(long id, long friendId) {
        entityIdValidator.checkUserId(id);
        entityIdValidator.checkUserId(friendId);
        userFriendsStorage.deleteFriend(id, friendId);
    }

    public List<User> getUserFriends(long id) {
        entityIdValidator.checkUserId(id);
        return userStorage.getUserFriends(id);
    }

    public List<User> getCommonFriends(long id, long otherId) {
        entityIdValidator.checkUserId(id);
        entityIdValidator.checkUserId(otherId);
        return userStorage.getCommonFriends(id, otherId);
    }
}
