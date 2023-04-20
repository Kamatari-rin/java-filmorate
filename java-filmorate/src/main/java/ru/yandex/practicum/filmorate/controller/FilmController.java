package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getTitleMessageCode(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/films")
    public List<Film> getFilmsList() {
        return new ArrayList<Film>(filmMap.values());
    }

    @PostMapping(value = "/films")
    public ResponseEntity<?> create(@Valid @RequestBody Film film) {
        film.setId(++id);
        filmMap.put(film.getId(), film);
        return new ResponseEntity<Film>(film, HttpStatus.OK);
    }

    @PutMapping(value = "/films/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid @NotNull Film film) {
        if (filmMap.get(id) == null) {
            return new ResponseEntity<>("The film with this id does not exist.", HttpStatus.BAD_REQUEST);
        }

        film.setId(id);
        filmMap.put(film.getId(), film);
        return new ResponseEntity<Film>(film, HttpStatus.OK);
    }
}
