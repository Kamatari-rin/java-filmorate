package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

public interface FilmStorage {
    Film update(Film film);
    Film add(Film film);
    Map<Long, Film> getFilmsMap();
}
