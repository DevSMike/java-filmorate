package ru.yandex.practicum.filmorate.validators.username;

import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<CheckUsername, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return true;
    }
}
