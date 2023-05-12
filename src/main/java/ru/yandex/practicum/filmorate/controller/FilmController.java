package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FilmController {

    @Autowired
    private FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

////////////////////////////////////////////    GET MAPPING    /////////////////////////////////////////////////////////

    @GetMapping("/films")
    public List<Film> getFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam int count) {
        return filmService.getCountPopularFilms(count);
    }

///////////////////////////////////////////    POST MAPPING    /////////////////////////////////////////////////////////

    @PostMapping("/films")
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        try {
            return new ResponseEntity<Film>(filmService.addFilm(film), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Error when trying to add a new movie: \n" + e.getMessage());
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

////////////////////////////////////////////    PUT MAPPING    /////////////////////////////////////////////////////////

    @PutMapping("/films")
    public ResponseEntity<Film> update(@RequestBody @Valid @NotNull Film film) {
        try {
            return new ResponseEntity<Film>(filmService.updateFilm(film), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Request for movie with non-existent id:" + film.getId() + ".");
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/films/{id}/like/{userid}")
    public ResponseEntity<Film> likeTheFilm(@PathVariable Long id, @PathVariable Long userid) {
        try {
            log.debug("Like was added successfully. [Film id: " + id + "], [User id: " + userid + "].");
            return new ResponseEntity<Film>(filmService.addLike(id, userid), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("The user or movie with this id does not exist: " +
                    "[Film id: " + id + "], [User id: " + userid + "].");
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

/////////////////////////////////////////    DELETE MAPPING    /////////////////////////////////////////////////////////

    @DeleteMapping("/films/{id}/like/{userId}")
    public ResponseEntity<Film> deleteUserLike(@PathVariable Long id, @PathVariable Long userId) {
        try {
            return new ResponseEntity<Film>(filmService.removeLike(id, userId), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("The user or movie with this id does not exist: " +
                    "[Film id: " + id + "], [User id: " + userId + "]" + ".");
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
