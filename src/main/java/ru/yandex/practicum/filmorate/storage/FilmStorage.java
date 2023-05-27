package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Map;

public interface FilmStorage {

    Film update(Film film);

    Film add(Film film);

    Map<Long, Film> getFilmsMap();

    Film getFilmByID(Long id);

    List<Film> getFilmsList();

    List<Film> getPopularFilmsList(int count);

    List<Genre> getAllGenres();

    Genre getGenreByID(int id);

    List<MpaRating> getAllMpaRatings();

    MpaRating getMpaRatingById(int id);
}
