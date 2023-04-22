package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class UserBirthdayValidator implements ConstraintValidator<UserBirthday, LocalDate> {
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (value == null) {
                return false;
            }
            if (value.isAfter(LocalDate.now())) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }
}
