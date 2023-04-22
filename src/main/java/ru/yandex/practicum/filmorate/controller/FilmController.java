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

    Map<Long, Film> filmMap = new HashMap<>();
    private Long id = 0L;

    CustomRestExceptionHandler customRestExceptionHandler  = new CustomRestExceptionHandler();

    @GetMapping("/films")
    public List<Film> getFilmsList() {
        return new ArrayList<Film>(filmMap.values());
    }

    @PostMapping(value = "/films")
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        film.setId(++id);
        filmMap.put(film.getId(), film);
        return new ResponseEntity<Film>(film, HttpStatus.OK);
    }

    @PutMapping(value = "/films")
    public ResponseEntity<?> update(@RequestBody @Valid @NotNull Film film) {
        try {
            if (filmMap.get(film.getId()) != null) {
                filmMap.put(film.getId(), film);
                return new ResponseEntity<Film>(film, HttpStatus.OK);
            } else throw new ValidationException("The film with this id does not exist.");
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
