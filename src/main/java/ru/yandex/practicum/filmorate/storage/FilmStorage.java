package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Optional<Film> updateFilm(Film film);

    Optional<Film> addFilm(Film film);

    Optional<Film> getFilmByID(Long id);

    Optional<List<Film>> getFilmsList();

    Optional<List<Film>> getPopularFilmsList(int count);

    Optional<List<Genre>> getAllGenres();

    Optional<Genre> getGenreByID(int id);

    Optional<List<MpaRating>> getAllMpaRatings();

    Optional<MpaRating> getMpaRatingById(int id);

    Optional<Film> likeFilm(Long filmId, Long userId);

    Optional<Film> removeUserLikeFromFilm(Long filmId, Long userId);
}
