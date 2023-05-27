package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FilmDurationValidator implements ConstraintValidator<FilmDuration, Integer> {

    @Override
    public boolean isValid(Integer duration, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (duration <= 0) {
                return false;
            } else return true;
        } catch (Exception e) {
            return false;
        }
    }
}
