package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Service
public class FilmService {

    @Autowired
    private FilmStorage filmStorage;

    public List<Film> getCountPopularFilms(Integer count) {
        if (count == null) {
            count = 10;
        }
        return Optional.of(filmStorage.getPopularFilmsList(count).orElseThrow()).get();
    }

    public Film addLike(Long filmId, Long userId) {
        return Optional.of(filmStorage.likeFilm(filmId, userId).orElseThrow()).get();
    }

    public Film removeLike(Long filmId, Long userId) {
        return Optional.of(filmStorage.removeUserLikeFromFilm(filmId, userId).orElseThrow()).get();
    }

    public Film addFilm(Film film) {
        return Optional.of(filmStorage.addFilm(film).orElseThrow()).get();
    }

    public List<Genre> getGenres() {
        return Optional.of(filmStorage.getAllGenres().orElseThrow()).get();
    }

    public Genre getGenreById(int id) {
        return Optional.of(filmStorage.getGenreByID(id).orElseThrow()).get();
    }

    public Film updateFilm(Film film) {
        return Optional.of(filmStorage.updateFilm(film).orElseThrow()).get();
    }

    public List<Film> getAllFilms() {
        return Optional.ofNullable(filmStorage.getFilmsList().orElseThrow()).get();
    }

    public Film getFilmById(Long id) {
        return Optional.of(filmStorage.getFilmByID(id).orElseThrow()).get();
    }

    public MpaRating getMpaRatingById(int id) {
        return Optional.of(filmStorage.getMpaRatingById(id).orElseThrow()).get();
    }

    public List<MpaRating> getMpaRatingList() {
        return Optional.of(filmStorage.getAllMpaRatings().orElseThrow()).get();
    }
}
