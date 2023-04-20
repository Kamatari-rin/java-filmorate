package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = releaseDateValidator.class)
@Documented
public @interface ReleaseDate {
    String message() default "{Release date should be after 1895.01.24.}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
