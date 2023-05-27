package ru.yandex.practicum.filmorate.storage.filmsdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.Map;
@Repository
public class FilmsDao implements FilmStorage {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Film update(Film film) {
        return null;
    }

    @Override
    public Film add(Film film) {
        return null;
    }

    @Override
    public Map<Long, Film> getFilmsMap() {
        return null;
    }

    @Override
    public Film getFilmByID(Long id) {
        return null;
    }

    @Override
    public List<Film> getFilmsList() {
        return null;
    }

    @Override
    public List<Film> getPopularFilmsList(int count) {
        return null;
    }

    @Override
    public List<Genre> getAllGenres() {
        return null;
    }

    @Override
    public Genre getGenreByID(int id) {
        return null;
    }

    @Override
    public List<MpaRating> getAllMpaRatings() {
        return null;
    }

    @Override
    public MpaRating getMpaRatingById(int id) {
        return null;
    }
}
