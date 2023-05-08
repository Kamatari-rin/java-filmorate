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
import java.util.List;

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
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        try {
            return new ResponseEntity<Film>(filmService.addFilm(film), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Error when trying to add a new movie: \n" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


////////////////////////////////////////////    PUT MAPPING    /////////////////////////////////////////////////////////

    @PutMapping("/films")
    public ResponseEntity<Film> update(@RequestBody @Valid @NotNull Film film) {
        try {
            return new ResponseEntity<Film>(filmService.updateFilm(film), HttpStatus.OK);
        } catch (Exception e){
            log.warn("Request for movie with non-existent id:" + film.getId() + ".");
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/films/{id}/like/{userId}")
    public ResponseEntity<Film> likeTheFilm(@PathVariable Long id, Long userId) {
        try {
            return new ResponseEntity<Film>(filmService.addLike(id, userId), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("The user or movie with this id does not exist: " +
                    "[Film id:" + id + "], [User id: " + userId + "]" + ".");
            throw new RuntimeException(e.getMessage());
        }
    }

/////////////////////////////////////////    DELETE MAPPING    /////////////////////////////////////////////////////////

    @DeleteMapping("/films/{id}/like/{userId}")
    public ResponseEntity<Film> deleteUserLike(@PathVariable Long id, Long userId) {
        try {
            return new ResponseEntity<Film>(filmService.removeLike(id, userId), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("The user or movie with this id does not exist: " +
                    "[Film id:" + id + "], [User id: " + userId + "]" + ".");
            throw new RuntimeException(e.getMessage());
        }
    }
}
