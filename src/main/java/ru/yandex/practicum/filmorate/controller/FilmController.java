package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

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
        return filmService.getFilmById(id);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
        return filmService.getCountPopularFilms(count);
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return filmService.getGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable int id) {
        return filmService.getGenreById(id);
    }

    @GetMapping("/mpa")
    public List<MpaRating> getAllMpa() {
        return filmService.getMpaRatingList();
    }

    @GetMapping("/mpa/{id}")
    public MpaRating getMpaById(@PathVariable int id) {
        return filmService.getMpaRatingById(id);
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
        return new ResponseEntity<Film>(filmService.addLike(id, userid), HttpStatus.OK);
    }

/////////////////////////////////////////    DELETE MAPPING    /////////////////////////////////////////////////////////

    @DeleteMapping("/films/{id}/like/{userId}")
    public ResponseEntity<Film> deleteUserLike(@PathVariable Long id, @PathVariable Long userId) {
        return new ResponseEntity<Film>(filmService.removeLike(id, userId), HttpStatus.OK);
    }
}
