package ru.yandex.practicum.filmorate.storage.user.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDbFriends implements UserFriends {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(long id, long friendId) {
        String sql = "INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (?, ?);";
        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public void deleteFriend(long id, long friendId) {
        String sql = "DELETE FROM USER_FRIENDS WHERE USER_ID = ? AND FRIEND_ID =?;";
        jdbcTemplate.update(sql, id, friendId);
    }
}
