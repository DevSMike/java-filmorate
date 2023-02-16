package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Film {


    int id;
    @NonNull
    String name;
    @NonNull
    String description;
    @NonNull
    LocalDate releaseDate;
    @NonNull
    Integer duration;
}
