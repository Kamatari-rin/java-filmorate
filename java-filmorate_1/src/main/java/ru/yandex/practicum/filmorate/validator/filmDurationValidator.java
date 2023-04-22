package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;

public class filmDurationValidator implements ConstraintValidator<FilmDuration, Duration> {
    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (duration.isNegative() || duration == null) {
                return false;
            } else return true;
        } catch (Exception e) {
            return false;
        }
    }
}
