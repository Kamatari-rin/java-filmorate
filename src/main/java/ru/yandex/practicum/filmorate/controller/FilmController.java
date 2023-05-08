package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {

    private FilmService filmService;

////////////////////////////////////////////    GET MAPPING    /////////////////////////////////////////////////////////

    @GetMapping("/films")
    public List<Film> getFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/films/popular?count={count}")
    public List<Film> getPopularFilms() {
        return new ArrayList<>();
    }

///////////////////////////////////////////    POST MAPPING    /////////////////////////////////////////////////////////

    @PostMapping("/films")
    public ResponseEntity<?> create(@Valid @RequestBody Film film) {
        try {
            filmService.createNewFilm(film);
            log.debug("The film " + film.getName() + " has been successfully created.");
            return new ResponseEntity<Film>(film, HttpStatus.OK);
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }


////////////////////////////////////////////    PUT MAPPING    /////////////////////////////////////////////////////////

    @PutMapping("/films")
    public ResponseEntity<Film> update(@RequestBody @Valid @NotNull Film film) {
        try {
            filmService.updateFilm(film);
            log.debug("The film " + film.getName() + " has been successfully updated.");
            return new ResponseEntity<Film>(film, HttpStatus.OK);
        } catch (Exception e){
            log.warn("Request for movie with non-existent id:" + film.getId() + ".");
            throw new ValidationException(e.getMessage());
        }
    }

    @PutMapping("/films/{id}/like/{userId}")
    public ResponseEntity<Film> likeTheFilm(@PathVariable Long filmId, Long userid) {
        try {
            filmService.addLike(filmId, userid);
            return new ResponseEntity<Film>(filmService.getFilmById(filmId), HttpStatus.OK);
        } catch (NullPointerException e) {
            log.warn("Request for movie or user with non-existent id: " +
                    "[Film id:" + filmId + "], [User id: " + userid + "]" + ".");
            throw new ValidationException(e.getMessage());
        }
    }
}
