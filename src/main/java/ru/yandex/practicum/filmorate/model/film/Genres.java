package ru.yandex.practicum.filmorate.model.film;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genres {
    private int id;
    private String name;

    public Genres(int id, String name) {
        this.name = name;
        this.id = id;
    }
}
