package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
public class FilmService {

    @Autowired
    private FilmStorage filmStorage;
    @Autowired
    private UserStorage userStorage;

    public List<Film> getCountPopularFilms(Integer count) {
        if (count == null) {
            count = 10;
        }
        return filmStorage.getPopularFilmsList(count).get();
    }

    public Film addLike(Long filmId, Long userId) {
        return filmStorage.likeFilm(filmId, userId).get();
    }

    public Film removeLike(Long filmId, Long userId) {
        userStorage.getUserById(userId);
        return filmStorage.removeUserLikeFromFilm(filmId, userId).get();
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film).get();
    }

    public List<Genre> getGenres() {
        return filmStorage.getAllGenres().get();
    }

    public Genre getGenreById(int id) {
        return filmStorage.getGenreByID(id).get();
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film).get();
    }

    public List<Film> getAllFilms() {
        return filmStorage.getFilmsList().get();
    }

    public Film getFilmById(Long id) {
        return filmStorage.getFilmByID(id).get();
    }

    public MpaRating getMpaRatingById(int id) {
        return filmStorage.getMpaRatingById(id).get();
    }

    public List<MpaRating> getMpaRatingList() {
        return filmStorage.getAllMpaRatings().get();
    }
}
