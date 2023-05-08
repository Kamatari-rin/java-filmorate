package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage{

    private final Map<Long, Film> filmMap = new HashMap<>();
    private Long id = 0L;

    @Override
    public Film add(Film film) {
        film.setId(++id);
        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public boolean isFilmExist(Long id) {
        if (filmMap.containsKey(id)) {
            return true;
        } else return false;
    }

    @Override
    public Map<Long, Film> getFilmsMap() {
        return filmMap;
    }

    @Override
    public Film getFilmById(Long id) {
        return filmMap.get(id);
    }
}
