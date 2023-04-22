package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

    private final Map<Long, User> userMap = new HashMap<>();
    private Long id = 0L;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getTitleMessageCode(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/users")
    public List<User> getUsersList() {
        log.debug("The user list was successfully retrieved.");
        return new ArrayList<User>(userMap.values());
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(++id);
        userMap.put(user.getId(), user);
        log.debug("User " + user.getName() + " has been successfully created.");
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid @NotNull User user) {
        if (userMap.get(user.getId()) == null) {
            log.error("Updating a user with a non-existent id.");
            return new ResponseEntity<>("The user with this id does not exist.", HttpStatus.BAD_REQUEST);
        }

        user.setId(id);
        userMap.put(user.getId(), user);
        log.debug("User " + user.getName() + "has been successfully updated.");
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
