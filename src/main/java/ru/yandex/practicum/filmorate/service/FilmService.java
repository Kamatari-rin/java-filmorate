package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmStorage filmStorage;
    @Autowired
    private UserService userService;

    public Film addLike(Long filmId, Long userId ) {
        userService.isUserExist(userId);
        isFilmExist(filmId);

        getFilmById(filmId)
                .getLikes()
                .add(userId);

        return filmStorage
                .getFilmsMap()
                .get(filmId);
    }

    public Film removeLike(Long filmId, Long userId) {
        userService.isUserExist(userId);
        isFilmExist(filmId);

        getFilmById(filmId)
                .getLikes()
                .remove(userId);

        return filmStorage
                .getFilmsMap()
                .get(filmId);
    }

    public Film addFilm(Film film) {
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) {
        isFilmExist(film.getId());
        return filmStorage.update(film);
    }

    public List<Film> getAllFilms() {
        if (filmStorage.getFilmsMap() != null) {
            return new ArrayList<Film>(filmStorage.getFilmsMap().values());
        } else throw new NullPointerException("Not a single movie was found.");
    }

    public Film getFilmById(Long id) {
        isFilmExist(id);
        return filmStorage
                .getFilmsMap()
                .get(id);
    }

    public void isFilmExist(Long id) {
        if (!filmStorage.getFilmsMap().containsKey(id)) {
            throw new ValidationException("The movie with this id does not exist: " +
                    "[Film id: " + id + "].");
        }
    }
}
