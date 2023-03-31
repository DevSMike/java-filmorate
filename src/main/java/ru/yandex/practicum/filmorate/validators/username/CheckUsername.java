package ru.yandex.practicum.filmorate.validators.username;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface CheckUsername {

    String message() default "{CapitalLetter.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}