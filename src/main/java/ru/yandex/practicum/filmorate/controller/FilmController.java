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

        log.info("GET all {} films", filmService.getAllFilms().size());
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable Long id) {

        log.info("Get a film by id: {} ", id);
        return filmService.getFilmById(id);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
        log.info("Get {} a popular films.", count);
        return filmService.getCountPopularFilms(count);
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        log.info("Get list of genres.");
        return filmService.getGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable int id) {

        log.info("Get a genre by id: {} ", id);
        return filmService.getGenreById(id);
    }

    @GetMapping("/mpa")
    public List<MpaRating> getAllMpa() {

        log.info("Get all MPA Rartins ");
        return filmService.getMpaRatingList();
    }

    @GetMapping("/mpa/{id}")
    public MpaRating getMpaById(@PathVariable int id) {
        log.info("Get a MPA rating by id: {} ", id);
        return filmService.getMpaRatingById(id);
    }

///////////////////////////////////////////    POST MAPPING    /////////////////////////////////////////////////////////

    @PostMapping("/films")
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        log.info("Create a film with id = {} ", film.getId());
        return new ResponseEntity<Film>(filmService.addFilm(film), HttpStatus.OK);
    }

////////////////////////////////////////////    PUT MAPPING    ////////////////////////////////////////////////////////

    @PutMapping("/films")
    public ResponseEntity<Film> update(@RequestBody @Valid @NotNull Film film) {
        log.info("The film with id = {}{}", film.getId(), " has been updated");
        return new ResponseEntity<Film>(filmService.updateFilm(film), HttpStatus.OK);
    }

    @PutMapping("/films/{id}/like/{userid}")
    public void likeTheFilm(@PathVariable Long id, @PathVariable Long userid) {
        log.info("The user with id = {} {} {} ", userId, " added like for the film with id = ", id);
        filmService.addLike(id, userid);
    }

/////////////////////////////////////////    DELETE MAPPING    /////////////////////////////////////////////////////////

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteUserLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("The user with id = {} {} {} ", userId, " has been removed like for the film with id: ", id);
        filmService.removeLike(id, userId);
    }
}
