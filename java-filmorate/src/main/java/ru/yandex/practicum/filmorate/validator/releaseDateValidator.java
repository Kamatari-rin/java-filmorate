package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.validator.ReleaseDate;

import java.time.LocalDate;

public class releaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {
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
