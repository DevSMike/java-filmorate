package ru.yandex.practicum.filmorate.validators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.Assert.assertEquals;

class UserValidatorTest {
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    @Test
    public void shouldBeUserWhenDataIsCorrect() {
        User user = User.builder()
                .email("mrmike@mail.ru")
                .login("mike123")
                .birthday(LocalDate.of(2002,6,18))
                .build();


        assertDoesNotThrow(() ->  UserValidator.validate(user), "Exception throws");
    }

    @Test
    public void shouldThrowExceptionWhileIncorrectLogin() {
        User user = User.builder()
                .email("mrmike@mail.ru")
                .login("mi ke")
                .birthday(LocalDate.of(2002,6,18))
                .build();

        ValidationException e = assertThrows(ValidationException.class
                ,() -> UserValidator.validate(user),"Exceptions does not throw");
        assertEquals(e.getMessage(), "Login is incorrect", "Messages are different");
    }

    @Test
    public void shouldThrowExceptionWhileIncorrectBirthday() {
        User user = User.builder()
                .email("mrmike@mail.ru")
                .login("mike")
                .birthday(LocalDate.of(2052,6,18))
                .build();
        ValidationException e = assertThrows(ValidationException.class
                ,() -> UserValidator.validate(user));
        assertEquals(e.getMessage(), "BirthDate is incorrect", "Messages are different");
    }

    @Test
    public void shouldThrowExceptionWhileIncorrectEmail() {
        User user = User.builder()
                .email("mrmike.cmail.ru")
                .login("mike")
                .birthday(LocalDate.of(2002,6,18))
                .build();
        ValidationException e = assertThrows(ValidationException.class
                ,() -> UserValidator.validate(user), "Exceptions does not throw");
        assertEquals(e.getMessage(), "Email is incorrect","Messages are different");
    }

    @Test
    public void shouldBeNameEqualsLoginWhileNameIsEmpty() {
        User user = User.builder()
                .email("mrmike@mail.ru")
                .login("mike")
                .birthday(LocalDate.of(2002,6,18))
                .build();
        UserValidator.validate(user);
        assertEquals(user.getLogin(), user.getName());

    }

    @Test
    public void shouldBeEqualsObjectsAfterUpdateIfValidated() {
        User user = User.builder()
                .email("mrmike@mail.ru")
                .login("mike")
                .birthday(LocalDate.of(2002,6,18))
                .build();
        Map<Long, User> users= new HashMap<>();
        UserValidator.validate(user);
        users.put(user.getId(), user);
        String json = "{\n" +
                "  \"login\": \"doloreUpdate\",\n" +
                "  \"name\": \"est adipisicing\",\n" +
                "  \"id\": 1,\n" +
                "  \"email\": \"mail@yandex.ru\",\n" +
                "  \"birthday\": \"1976-09-20\"\n" +
                "}";
        User user2 = gson.fromJson(json, User.class);
        UserValidator.validate(user2);
        users.put(user2.getId(), user2);
        assertEquals(user2.getName(), users.get(user2.getId()).getName(), "Objects are different");
    }

    @Test
    public void shouldBeExceptionAfterUnknownUpdate() {
        User user = User.builder()
                .email("mrmike@mail.ru")
                .login("mike")
                .birthday(LocalDate.of(2002,6,18))
                .build();
        Map<Long, User> users= new HashMap<>();
        UserValidator.validate(user);
        users.put(user.getId(), user);
        String json = "{\n" +
                "  \"login\": \"doloreUpdate\",\n" +
                "  \"name\": \"est adipisicing\",\n" +
                "  \"id\": 9999,\n" +
                "  \"email\": \"mail@yandex.ru\",\n" +
                "  \"birthday\": \"1976-09-20\"\n" +
                "}";
    }
}
