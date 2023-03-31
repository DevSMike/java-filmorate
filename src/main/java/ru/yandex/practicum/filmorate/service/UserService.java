package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.storage.user.friends.UserFriends;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final UserFriends userFriendsStorage;
    private final UserValidator userValidator;

    public void addUser(User user) {
        userStorage.add(user);
    }

    public void updateUser (User user) {
        userValidator.validateId(user.getId());
        userStorage.update(user);
    }

    public void deleteUser(User user) {
        userValidator.validateId(user.getId());
        userStorage.delete(user);
    }

    public User getUserById(long id) {
        userValidator.validateId(id);
        return userStorage.getUserById(id);
    }

    public List<User> getUsersInList() {
        return userStorage.getUsersList();
    }


    public void addFriend(long id, long friendId) {
        userValidator.validateId(id);
        userValidator.validateId(friendId);
        userFriendsStorage.addFriend(id, friendId);
    }

    public void deleteFriend(long id, long friendId) {
        userValidator.validateId(id);
        userValidator.validateId(friendId);
        userFriendsStorage.deleteFriend(id, friendId);
    }


    public List<User> getUserFriends(long id) {
        userValidator.validateId(id);
       return userStorage.getUserFriends(id);
    }

    public List<User> getCommonFriends(long id, long otherId) {
        userValidator.validateId(id);
        userValidator.validateId(otherId);
        return userStorage.getCommonFriends(id, otherId);
    }

}
