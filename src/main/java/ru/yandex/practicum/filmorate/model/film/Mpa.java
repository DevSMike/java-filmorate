package ru.yandex.practicum.filmorate.model.film;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Mpa {

    private int id;
    @NotBlank
    private String name;

    public Mpa(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

