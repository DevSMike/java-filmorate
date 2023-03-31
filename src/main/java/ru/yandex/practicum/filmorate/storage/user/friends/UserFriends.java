package ru.yandex.practicum.filmorate.storage.user.friends;


public interface UserFriends {

    void addFriend(long id, long friendId);

    void deleteFriend(long id, long friendId) ;


}
