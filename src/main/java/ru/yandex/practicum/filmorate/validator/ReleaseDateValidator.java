package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (value == null) {
                return false;
            }
            if (value.isBefore(LocalDate.of(1895,1, 28))) {
                return false;
            } else return true;
        } catch (Exception e) {
            return false;
        }
    }
}
