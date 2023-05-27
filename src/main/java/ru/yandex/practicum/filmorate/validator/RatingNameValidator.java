package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RatingNameValidator implements ConstraintValidator<RatingName, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (s.equals("Комедия")
             || s.equals("Драма")
             || s.equals("Мультфильм")
             || s.equals("Триллер")
             || s.equals("Документальный")
             || s.equals("Боевик")) {
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }
    }
}
