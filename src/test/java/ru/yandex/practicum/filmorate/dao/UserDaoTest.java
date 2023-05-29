package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.usersdao.UsersDao;

import javax.validation.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserDaoImplTest {
    private final UsersDao userStorage;
    private User userOne;
    private User userSecond;
    private Validator validator;
    private Set<Long> friends = new HashSet<>();

    private final JdbcTemplate jdbcTemplate;


    @BeforeEach
    void beforeEach() {
        userSecond = new User("UserSecond@mail.com", "UserSecond_login", "UserSecond_name",
                LocalDate.of(2000, 1, 1));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void afterEach() {
        String sqlQuery =
                "delete from FRIENDS;\n" +
                        "delete from FILMS;\n" +
                        "delete from USERS;\n" +
                        "delete from FILM_LIKE;\n" +
                        "delete from GENRE_FILM;";
        jdbcTemplate.update(sqlQuery);
    }

    @Test
    void shouldCreateUser() {
        userOne = new User("User@email.com", "User_login", "User_name",
                LocalDate.of(1993, 11, 11));
        userStorage.create(userOne);
        assertEquals("User_login", userOne.getLogin(), "User's login isn't correct");
        assertEquals("User_name", userOne.getName(), "User's name isn't correct");
        assertEquals("User@email.com", userOne.getEmail(), "User's email isn't correct");
        assertEquals(LocalDate.of(1993, 11, 11), userOne.getBirthday(), "User's name isn't correct");
    }

    @Test
    void shouldNotCreateUserWithNullEmail() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            userSecond.setEmail(null);
            Set<ConstraintViolation<User>> violations = validator.validate(userSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("email: email cannot be empty.", exception.getMessage());
    }

    @Test
    void shouldNotCreateUserWithFailEmail() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            userSecond.setEmail("mail.ru");
            Set<ConstraintViolation<User>> violations = validator.validate(userSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("email: Incorrect email.", exception.getMessage());
    }

    @Test
    void shouldNotCreateUserWithFailLogin() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            userSecond.setLogin("");
            Set<ConstraintViolation<User>> violations = validator.validate(userSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("login: User login cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldNotCreateUserWithNullBirthday() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            userSecond.setBirthday(null);
            Set<ConstraintViolation<User>> violations = validator.validate(userSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("birthday: The user's date of birth must not be in the future.", exception.getMessage());
    }

    @Test
    void shouldNotCreateUserWithIncorrectBirthday() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            userSecond.setBirthday(LocalDate.of(2895, 12, 28));
            Set<ConstraintViolation<User>> violations = validator.validate(userSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("birthday: The user's date of birth must not be in the future.", exception.getMessage());
    }

    @Test
    void shouldNotUpdateUserWithNullEmail() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            userStorage.create(userSecond);
            userSecond.setEmail(null);
            Set<ConstraintViolation<User>> violations = validator.validate(userSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("email: email cannot be empty.", exception.getMessage());
    }

    @Test
    void shouldNotUpdateUserWithFailEmail() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            userStorage.create(userSecond);
            userSecond.setEmail("@mail.ru");
            Set<ConstraintViolation<User>> violations = validator.validate(userSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("email: Incorrect email.", exception.getMessage());
    }

    @Test
    void shouldNotUpdateUserWithFailLogin() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            userStorage.create(userSecond);
            userSecond.setLogin("");
            Set<ConstraintViolation<User>> violations = validator.validate(userSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("login: User login cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldNotUpdateUserWithIncorrectBirthday() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            userStorage.create(userSecond);
            userSecond.setBirthday(LocalDate.of(2895, 12, 28));
            Set<ConstraintViolation<User>> violations = validator.validate(userSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("birthday: The user's date of birth must not be in the future.", exception.getMessage());
    }

    @Test
    void shouldUpdateUserWithEmptyName() {
        userStorage.create(userSecond);
        userSecond.setName("");

        assertEquals("UserSecond_login", userSecond.getLogin(), "User's name isn't correct");

    }
}