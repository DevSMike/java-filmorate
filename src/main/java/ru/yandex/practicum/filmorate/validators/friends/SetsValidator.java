package ru.yandex.practicum.filmorate.validators.friends;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.LinkedHashSet;

@Slf4j
public class SetsValidator implements ConstraintValidator<InitializeField, Object> {

    String type;

    @Override
    public void initialize(InitializeField annotation) {
        this.type = annotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        switch (type) {
            case "user": {
                User user = (User) o;
                if (user.getFriends() == null) {
                    user.setFriends(new HashSet<>());
                }
                return true;
            }
            case "film": {
                Film film = (Film) o;
                if (film.getLikes() == null) {
                    film.setLikes(new HashSet<>());
                }
                if (film.getGenres() == null) {
                    film.setGenres(new LinkedHashSet<>());
                }
                return true;
            }
            default: {
                log.debug("Not supported type");
            }
        }
        return false;
    }
}
