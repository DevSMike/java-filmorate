package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Primary
@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(User user) {
        String sql = "INSERT INTO USERS (USER_NAME, EMAIL, LOGIN, BIRTHDAY) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
        long id = jdbcTemplate.queryForObject("SELECT USER_ID FROM  USERS ORDER BY USER_ID DESC LIMIT 1;", Integer.class);
        user.setId(id);
        Optional<Set<Long>> userLikes = Optional.ofNullable(user.getFriends());
        if (userLikes.isEmpty()) {
            return;
        }
        for (Long aLong : userLikes.get()) {
            jdbcTemplate.update("INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (?,?)", user.getId(), aLong);
        }
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("UPDATE USERS SET USER_NAME = ?, EMAIL = ?, LOGIN = ?, BIRTHDAY = ? WHERE USER_ID = ?;",
                user.getName(), user.getEmail(), user.getLogin(), user.getBirthday(), user.getId());
    }

    @Override
    public void delete(User user) {
        jdbcTemplate.update("DELETE FROM USERS WHERE user_id =?;", user.getId());
    }

    @Override
    public List<User> getUsersList() {
        String sql = "SELECT u.USER_ID, u.USER_NAME , u.EMAIL, u.LOGIN, u.BIRTHDAY, STRING_AGG(uf.FRIEND_ID, ',')" +
                " AS friends FROM USERS u LEFT JOIN USER_FRIENDS AS uf ON u.user_id = uf.user_id GROUP BY u.user_id;";
        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs, sql)));
    }

    @Override
    public Map<Long, User> getUsersMap() {
        return getUsersList().stream().collect(Collectors.toMap(User::getId, user -> user));
    }

    @Override
    public List<User> getCommonFriends(long id, long otherId) {
        String sql = " SELECT u.USER_ID, u.USER_NAME , u.EMAIL, u.LOGIN, u.BIRTHDAY FROM USERS u " +
                "WHERE u.USER_ID IN (SELECT uf.FRIEND_ID FROM USER_FRIENDS uf " +
                "GROUP BY uf.FRIEND_ID " +
                "HAVING STRING_AGG(uf.USER_ID , '') LIKE ? AND STRING_AGG(uf.USER_ID , '') LIKE ? ); ";
        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs, sql),
                "%" + id + "%", "%" + otherId + "%"));
    }

    @Override
    public List<User> getUserFriends(long id) {
        String sql = "SELECT u.USER_ID, u.USER_NAME , u.EMAIL, u.LOGIN, u.BIRTHDAY FROM USERS u " +
                "WHERE u.USER_ID IN (SELECT uf.FRIEND_ID FROM USER_FRIENDS uf WHERE uf.user_id = ?);";
        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs, sql), id));
    }

    @Override
    public User getUserById(long id) {
        String sql = "SELECT u.USER_ID, u.USER_NAME , u.EMAIL, u.LOGIN, u.BIRTHDAY FROM USERS u WHERE u.USER_ID = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeUser(rs, sql), id);
    }

    private User makeUser(ResultSet rs, String sql) throws SQLException {
        Set<Long> friends = new HashSet<>();
        if (sql.contains("friends")) {
            Optional<Array> userFriends = Optional.ofNullable(rs.getArray("friends"));
            if (userFriends.isPresent()) {
                friends = Arrays.stream((Object[]) userFriends.get().getArray()).map(Object::toString)
                        .flatMap(Pattern.compile(",")::splitAsStream).map(Long::valueOf)
                        .collect(Collectors.toSet());
            }
        }
        return User.builder()
                .id(rs.getInt("user_id"))
                .name(rs.getString("user_name"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .friends(friends)
                .build();
    }
}
