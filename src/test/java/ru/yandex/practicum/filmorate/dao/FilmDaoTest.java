package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.filmsdao.FilmsDao;
import ru.yandex.practicum.filmorate.storage.usersdao.UsersDao;


import javax.validation.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmDaoTest {
    private final FilmsDao filmStorage;
    public final UsersDao userStorage;
    private Film filmOne;
    private Film filmSecond;
    private Validator validator;
    private final JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        MpaRating rating = new MpaRating();
        rating.setId(1L);
        rating.setName("G");
        filmSecond = new Film("Chicken Run", "Chicken Run is a 2000 stop-motion animated adventure " +
                "comedy film produced by Pathé ",
                LocalDate.of(2000, 12, 28), 120,
                rating, new HashSet<>());

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
    void shouldCreateFilm() {
        MpaRating rating = new MpaRating();
        rating.setId(3L);
        rating.setName("PG-13");
        filmOne = new Film("Film_name", "Film_description",
                LocalDate.of(1987, 2, 20), 120,
                rating, new HashSet<>());
        filmStorage.addFilm(filmOne);
        assertEquals("Film_name", filmOne.getName(), "Film_name");
        assertEquals("Film_description", filmOne.getDescription(), "Film_description");
        assertEquals(120, filmOne.getDuration(), "Film's duration  isn't correct");
        assertEquals(LocalDate.of(1987, 2, 20), filmOne.getReleaseDate(),
                "Film's release date  isn't correct");
    }

    @Test
    void shouldNotCreateFilmWithEmptyName() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            filmSecond.setName("");
            Set<ConstraintViolation<Film>> violations = validator.validate(filmSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("name: Name cannot be empty.", exception.getMessage());
    }

    @Test
    void shouldNotCreateFilmWithDescriptionMoreThan200symbols() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            String description = "Film_description >200 for test: Кстати, базовые сценарии поведения пользователей " +
                    "разоблачены. Вот вам яркий пример современных тенденций — перспективное планирование в " +
                    "значительной степени обусловливает важность глубокомысленных рассуждений. Предварительные выводы " +
                    "неутешительны: укрепление и развитие внутренней структуры прекрасно подходит " +
                    "для реализации стандартных подходов.";
            filmSecond.setDescription(description);
            Set<ConstraintViolation<Film>> violations = validator.validate(filmSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("description: The description length is 200 characters.",
                exception.getMessage());
    }

    @Test
    void shouldNotCreateFilmWithNullReleaseDate() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            filmSecond.setReleaseDate(null);
            Set<ConstraintViolation<Film>> violations = validator.validate(filmSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("releaseDate: The film's release date must be after 1894-01-24.", exception.getMessage());
    }

    @Test
    void shouldNotCreateFilmNegativeDuration() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            filmSecond.setDuration(-1);
            Set<ConstraintViolation<Film>> violations = validator.validate(filmSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("duration: Film duration should be positive.", exception.getMessage());
    }

    @Test
    void shouldUpdateFilm() {
        filmStorage.addFilm(filmSecond);
        filmSecond.setName("New name");
        filmSecond.setDescription("New description");
        filmSecond.setReleaseDate((LocalDate.of(2005, 12, 28)));
        filmSecond.setDuration(24356);
        assertEquals("New name", filmSecond.getName(), "Film's name isn't correct");
        assertEquals("New description", filmSecond.getDescription(), "Film's description isn't correct");
        assertEquals(24356, filmSecond.getDuration(), "Film's duration  isn't correct");
        assertEquals(LocalDate.of(2005, 12, 28), filmSecond.getReleaseDate(), "Film's release date  isn't correct");
    }

    @Test
    void shouldNotUpdateFilmWithEmptyName() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            filmStorage.addFilm(filmSecond);
            filmSecond.setName("");
            Set<ConstraintViolation<Film>> violations = validator.validate(filmSecond);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });
        Assertions.assertEquals("name: Name cannot be empty.", exception.getMessage());
    }
}