package ru.yandex.practicum.filmorate.validators.exist;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ExistValidator.class)
@Documented
public @interface Exist {

    String message() default "{CapitalLetter.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}