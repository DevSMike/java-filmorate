package ru.yandex.practicum.filmorate.model.film;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validators.date.FilmRelease;
import ru.yandex.practicum.filmorate.validators.friends.InitializeField;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@InitializeField(message = "film")
public class Film {

    long id;
    @NotEmpty
    String name;
    @NonNull @Length(min = 1, max = 200, message = "Описание фильма не должно превышать 200 символов.")
    String description;
    @NonNull @PastOrPresent(message = "Дата не может быть в будущем")
    @FilmRelease
    LocalDate releaseDate;
    @NonNull @Min(value = 0, message = "Продолжительность фильма должна быть положительной")
    Integer duration;
    Set<Long> likes;
    @NonNull
    Mpa mpa;
    LinkedHashSet<Genres> genres;

    public int getLikesLength() {
        return likes.size();
    }
}
