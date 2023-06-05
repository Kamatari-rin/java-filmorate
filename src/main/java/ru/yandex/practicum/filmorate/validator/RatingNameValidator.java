package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RatingNameValidator implements ConstraintValidator<RatingName, String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (s.equals("G")
             || s.equals("PG")
             || s.equals("PG-13")
             || s.equals("R")
             || s.equals("NC-17")) {
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }
    }
}
