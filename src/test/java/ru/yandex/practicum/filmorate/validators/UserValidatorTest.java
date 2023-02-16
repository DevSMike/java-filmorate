package ru.yandex.practicum.filmorate.validators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
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
        User user = new User("mrmike@mail.ru", "mike123", LocalDate.of(2002, 6, 18));
        Map<Integer, User> users= new HashMap<>();

        assertDoesNotThrow(() ->  UserValidator.validate(user, users, HttpMethod.POST), "Exception throws");
    }

    @Test
    public void shouldThrowExceptionWhileIncorrectLogin() {
        User user = new User("mrmike@mail.ru", "mike mike", LocalDate.of(2002, 6, 18));
        Map<Integer, User> users= new HashMap<>();

        ValidationException e = assertThrows(ValidationException.class
                ,() -> UserValidator.validate(user, users, HttpMethod.POST),"Exceptions does not throw");
        assertEquals(e.getMessage(), "Login is incorrect", "Messages are different");
    }

    @Test
    public void shouldThrowExceptionWhileIncorrectBirthday() {
        User user = new User("mrmike@mail.ru", "mike", LocalDate.of(2042, 6, 18));
        Map<Integer, User> users= new HashMap<>();

        ValidationException e = assertThrows(ValidationException.class
                ,() -> UserValidator.validate(user, users, HttpMethod.POST));
        assertEquals(e.getMessage(), "BirthDate is incorrect", "Messages are different");
    }

    @Test
    public void shouldThrowExceptionWhileIncorrectEmail() {
        User user = new User("mrmike.mai.ru", "mike", LocalDate.of(2002, 6, 18));
        Map<Integer, User> users= new HashMap<>();

        ValidationException e = assertThrows(ValidationException.class
                ,() -> UserValidator.validate(user, users, HttpMethod.POST), "Exceptions does not throw");
        assertEquals(e.getMessage(), "Email is incorrect","Messages are different");
    }

    @Test
    public void shouldBeNameEqualsLoginWhileNameIsEmpty() {
        User user = new User("mrmike@mail.ru", "mike", LocalDate.of(2002, 6, 18));
        Map<Integer, User> users= new HashMap<>();

        UserValidator.validate(user, users, HttpMethod.POST);
        assertEquals(user.getLogin(), user.getName());

    }

    @Test
    public void shouldBeEqualsObjectsAfterUpdateIfValidated() {
        User user = new User("mrmike@mail.ru", "mike", LocalDate.of(2002, 6, 18));
        Map<Integer, User> users= new HashMap<>();

        UserValidator.validate(user, users, HttpMethod.POST);
        users.put(user.getId(), user);
        String json = "{\n" +
                "  \"login\": \"doloreUpdate\",\n" +
                "  \"name\": \"est adipisicing\",\n" +
                "  \"id\": 1,\n" +
                "  \"email\": \"mail@yandex.ru\",\n" +
                "  \"birthday\": \"1976-09-20\"\n" +
                "}";

        User user2 = gson.fromJson(json, User.class);
        UserValidator.validate(user2, users, HttpMethod.POST);

        users.put(user2.getId(), user2);
        assertEquals(user2.getName(), users.get(user2.getId()).getName(), "Objects are different");
    }

    @Test
    public void shouldBeExceptionAfterUnknownUpdate() {
        User user = new User("mrmike@mail.ru", "mike", LocalDate.of(2002, 6, 18));
        Map<Integer, User> users= new HashMap<>();

        UserValidator.validate(user, users, HttpMethod.POST);
        users.put(user.getId(), user);
        String json = "{\n" +
                "  \"login\": \"doloreUpdate\",\n" +
                "  \"name\": \"est adipisicing\",\n" +
                "  \"id\": 9999,\n" +
                "  \"email\": \"mail@yandex.ru\",\n" +
                "  \"birthday\": \"1976-09-20\"\n" +
                "}";

        User user2 = gson.fromJson(json, User.class);
        ValidationException e = assertThrows(ValidationException.class
                ,() -> UserValidator.validate(user2, users, HttpMethod.PUT), "Exceptions does not throw");
        assertEquals(e.getMessage(),"Id is incorrect", "Messages are different");

    }

}