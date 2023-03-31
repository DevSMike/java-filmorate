package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.friends.UserFriends;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.testng.Assert.assertEquals;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTest {
    private final UserDbStorage userStorage;
    private final UserFriends userFriends;
    private final JdbcTemplate jdbcTemplate;

    @Test
    void getUserById() {
        User user = User.builder()
                .login("antonio")
                .email("mymail@mail.ru")
                .name("mike123")
                .birthday(LocalDate.parse("2011-11-12"))
                .build();
        userStorage.add(user);
        long id = jdbcTemplate.queryForObject("SELECT USER_ID FROM  USERS ORDER BY USER_ID DESC LIMIT 1;"
                ,Integer.class);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(id));
        assertEquals(userOptional.get().getName(), user.getName(), "usernames not equlas");
    }

    @Test
    void add() {
        User user = User.builder()
                .login("avatar")
                .email("myMail@mail.ru")
                .name("name")
                .birthday(LocalDate.parse("2001-11-12"))
                .build();
        userStorage.add(user);
        long id = jdbcTemplate.queryForObject("SELECT USER_ID FROM  USERS ORDER BY USER_ID DESC LIMIT 1;"
                ,Integer.class);
        User getUser = userStorage.getUserById(id);
        assertEquals(user.getName(), getUser.getName(), "usernames not equlas");

    }

    @Test
    void update() {
        User user = User.builder()
                .login("Login")
                .birthday(LocalDate.parse("1990-12-12"))
                .name("Name")
                .email("newMail@mail.ru")
                .build();
        userStorage.add(user);
        long id = jdbcTemplate.queryForObject("SELECT USER_ID FROM  USERS ORDER BY USER_ID DESC LIMIT 1;"
                ,Integer.class);
        User userUpdate = User.builder()
                .id(id)
                .login("newLogin")
                .birthday(LocalDate.parse("1990-12-12"))
                .name("newName")
                .email("newMail@mail.ru")
                .build();
        userStorage.update(userUpdate);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(id));
        assertEquals(userUpdate.getName(), userOptional.get().getName(), "usernames not equlas");
    }

    @Test
    void delete() {
        User user = User.builder()
                .login("Login123")
                .birthday(LocalDate.parse("1991-12-12"))
                .name("Name123")
                .email("newNewMail@mail.ru")
                .build();
        userStorage.add(user);
        int len = userStorage.getUsersList().size();
        userStorage.delete(user);
        assertEquals(userStorage.getUsersList().size(), len-1, "lens are diffrerent");
    }

    @Test
    void getUsersList() {
        User user = User.builder()
                .login("Login2")
                .birthday(LocalDate.parse("1999-12-12"))
                .name("Name2")
                .email("newNMail@mail.ru")
                .build();
        userStorage.add(user);
        List<User> users = userStorage.getUsersList();
        assertEquals(users.size(), userStorage.getUsersList().size(), "size are different");
    }

    @Test
    void getUsersMap() {
        User user = User.builder()
                .login("Login000")
                .birthday(LocalDate.parse("2006-12-12"))
                .name("Name000")
                .email("new1Mail@mail.ru")
                .build();
        userStorage.add(user);
        Map<Long, User> users = userStorage.getUsersMap();
        assertEquals(users.size(), userStorage.getUsersMap().size(), "size are different");
    }

    @Test
    void getCommonFriends() {
        User user = User.builder()
                .login("L0g123")
                .birthday(LocalDate.parse("1971-12-12"))
                .name("N01")
                .email("n00ewMail@mail.ru")
                .build();
        User user2 = User.builder()
                .login("L0g222")
                .birthday(LocalDate.parse("1945-12-12"))
                .name("N02")
                .email("021NewMail@mail.ru")
                .build();
        User user3 = User.builder()
                .login("userNuilder")
                .birthday(LocalDate.parse("1946-12-12"))
                .name("user1112")
                .email("notNew@mail.ru")
                .build();
        userStorage.add(user);
        userStorage.add(user2);
        userStorage.add(user3);
        userFriends.addFriend(user.getId(), user3.getId());
        userFriends.addFriend(user2.getId(), user3.getId());
        assertEquals(userStorage.getCommonFriends(user.getId(), user2.getId()).get(0).getName(), user3.getName(), "names not eql");

    }

    @Test
    void getUserFriends() {
        User user = User.builder()
                .login("Log1")
                .birthday(LocalDate.parse("1990-12-12"))
                .name("N1")
                .email("new123ewMail@mail.ru")
                .build();
        User user2 = User.builder()
                .login("Log2")
                .birthday(LocalDate.parse("1992-12-12"))
                .name("N2")
                .email("23ewMail@mail.ru")
                .build();

        userStorage.add(user);
        userStorage.add(user2);
        userFriends.addFriend(user.getId(), user2.getId());

        assertEquals(userStorage.getUserFriends(user.getId()).get(0).getName(), user2.getName(), "names not eql");

    }


}