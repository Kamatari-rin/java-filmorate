package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Service
public class FilmService {

    @Autowired
    private FilmStorage filmStorage;

    public List<Film> getCountPopularFilms(Integer count) {
        List<Film> popularFilms = new ArrayList<>();
        return popularFilms;
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
}
