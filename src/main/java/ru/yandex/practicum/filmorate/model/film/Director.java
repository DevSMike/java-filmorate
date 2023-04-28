package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class Director {
    private final Long id;
    @NotBlank(message = "Name can't be null or empty")
    private final String name;
}