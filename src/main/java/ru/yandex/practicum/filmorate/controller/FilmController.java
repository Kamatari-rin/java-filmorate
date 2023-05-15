package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.AppException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@ControllerAdvice
@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

////////////////////////////////////////////    GET MAPPING    /////////////////////////////////////////////////////////

    @GetMapping("/films")
    public List<Film> getFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable Long id) {
        try {
            return filmService.getFilmById(id);
        } catch (Throwable e) {
            throw new AppException(e.getClass().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
        if (count == null) {
            return filmService.getCountPopularFilms(-1);
        } else return filmService.getCountPopularFilms(count);
    }

///////////////////////////////////////////    POST MAPPING    /////////////////////////////////////////////////////////

    @PostMapping("/films")
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        return new ResponseEntity<Film>(filmService.addFilm(film), HttpStatus.OK);
    }

////////////////////////////////////////////    PUT MAPPING    ////////////////////////////////////////////////////////

    @PutMapping("/films")
    public ResponseEntity<Film> update(@RequestBody @Valid @NotNull Film film) {
        return new ResponseEntity<Film>(filmService.updateFilm(film), HttpStatus.OK);
    }

    @PutMapping("/films/{id}/like/{userid}")
    public ResponseEntity<Film> likeTheFilm(@PathVariable Long id, @PathVariable Long userid) {
        try {
            return new ResponseEntity<Film>(filmService.addLike(id, userid), HttpStatus.OK);
        } catch (Throwable e) {
            throw new AppException(e.getClass().toString(), HttpStatus.NOT_FOUND);
        }
    }

/////////////////////////////////////////    DELETE MAPPING    /////////////////////////////////////////////////////////

    @DeleteMapping("/films/{id}/like/{userId}")
    public ResponseEntity<Film> deleteUserLike(@PathVariable Long id, @PathVariable Long userId) {
        return new ResponseEntity<Film>(filmService.removeLike(id, userId), HttpStatus.OK);
    }
}
