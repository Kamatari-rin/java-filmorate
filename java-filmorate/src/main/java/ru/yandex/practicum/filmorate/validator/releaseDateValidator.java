package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.time.LocalDate;

public class releaseDateValidator implements ConstraintValidator<ReleaseDate, String> {
    @Override
    public void initialize(ReleaseDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value.isBlank()) {
            return false;
        }
        try {
            LocalDate releaseDate = LocalDate.parse(value);
            if (releaseDate.isBefore(LocalDate.of(1895,1, 28))) {
                return false;
            } else return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
