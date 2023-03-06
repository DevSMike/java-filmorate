package ru.yandex.practicum.filmorate.validators.friends;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = SetsValidator.class)
@Documented
public @interface InitializeField {

    String message() default "{CapitalLetter.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}