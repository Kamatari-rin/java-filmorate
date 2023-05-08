package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    Film update(Film film);
    Film add(Film film);
    boolean isFilmExist(Long id);
    Map<Long, Film> getFilmsMap();

    Film getFilmById(Long id);
}
