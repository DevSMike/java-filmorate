package ru.yandex.practicum.filmorate.validators.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<FilmRelease, LocalDate> {

    private static final LocalDate FIRST_MOVIE_RELEASE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return !localDate.isBefore(FIRST_MOVIE_RELEASE);
    }
}
