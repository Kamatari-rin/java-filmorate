package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.UserBirthday;

import java.time.LocalDate;

@Data
public class User {
    private Long id;
    @Email(message = "Incorrect email.")
    @NotNull(message = "email cannot be empty.")
    private String email;
    @NotNull
    @NotBlank(message = "User login cannot be null or empty")
    private String login;
    private String name;
    @UserBirthday(message = "The user's date of birth must not be in the future.")
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;

        if (name == null || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }

        this.birthday = birthday;
    }
}
