package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private FilmStorage filmStorage;
    private UserService userService;

    public Film addLike(Long filmId, Long userId) {
        if (filmStorage.getFilmsMap().containsKey(filmId) && userService.getUsers().containsKey(userId)) {
            filmStorage.getFilmById(filmId)
                    .getLikes()
                    .add(userId);
            return filmStorage.getFilmById(filmId);
        } else throw new ValidationException("The user or movie with this id does not exist: " +
                "[Film id:" + filmId + "], [User id: " + userId + "]" + ".");
    }

    public Film removeLike(Long filmId, Long userId) {
        if (filmStorage.getFilmsMap().containsKey(filmId) && userService.getUsers().containsKey(userId)) {
            filmStorage.getFilmById(filmId)
                    .getLikes()
                    .remove(userId);
            return filmStorage.getFilmById(filmId);
        } else throw new ValidationException("The user or movie with this id does not exist: " +
                "[Film id:" + filmId + "], [User id: " + userId + "]" + ".");
    }

    public Film addFilm(Film film) {
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> getAllFilms() {
        return new ArrayList<Film>(filmStorage.getFilmsMap().values());
    }

    public Film getFilmById(Long id) {
        return filmStorage.getFilmById(id);
    }

    public void isFilmExist(Long id) {
        if (!filmStorage.isFilmExist(id)) {
            throw new NullPointerException("Фильм с id " + id + " не был найден.");
        }
    }
}
