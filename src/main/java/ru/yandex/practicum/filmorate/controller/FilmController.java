package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.CustomRestExceptionHandler;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
@RestController
public class FilmController {

    private final Map<Long, Film> filmMap = new HashMap<>();
    private Long id = 0L;

    @GetMapping("/films")
    public List<Film> getFilmsList() {
        return new ArrayList<Film>(filmMap.values());
    }

    @PostMapping(value = "/films")
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        film.setId(++id);
        filmMap.put(film.getId(), film);
        log.debug("The film " + film.getName() + " has been successfully created.");
        return new ResponseEntity<Film>(film, HttpStatus.OK);
    }

    @PutMapping(value = "/films")
    public ResponseEntity<?> update(@RequestBody @Valid @NotNull Film film) {
        try {
            if (filmMap.get(film.getId()) != null) {
                filmMap.put(film.getId(), film);
                log.debug("The film " + film.getName() + " has been successfully updated.");
                return new ResponseEntity<Film>(film, HttpStatus.OK);
            } else {
                log.warn("TRequest for movie with non-existent id:" + film.getId());
                throw new ValidationException("The film with " + film.getId() + " id does not exist.");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
