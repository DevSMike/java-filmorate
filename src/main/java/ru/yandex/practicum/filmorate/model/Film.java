package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {

    long id;
    @NonNull
    String name;
    @NonNull @Length(min = 1, max = 200, message = "Описание фильма не должно превышать 200 символов.")
    String description;
    @NonNull @PastOrPresent(message = "Дата не может быть в будущем")
    LocalDate releaseDate;
    @NonNull @Min(value = 0, message = "Продолжительность фильма должна быть положительной")
    Integer duration;
    Set<Long> likes;

    public int getLikesLength() {
        return likes.size();
    }
}
