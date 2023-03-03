package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {

    long id;
    String name;
    @NonNull @Email(message = "Введено некорректное значение электронной почты.")
    String email;
    @NonNull @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp ="\\S*", message = "Логин не может содержать пробелы.")
    String login;
    @NonNull  @PastOrPresent(message = "Дата не может быть в будущем")
    LocalDate birthday;
    Set<Long> friends;
}
