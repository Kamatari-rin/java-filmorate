package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.CustomRestExceptionHandler;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

    private final Map<Long, User> userMap = new HashMap<>();

    CustomRestExceptionHandler customRestExceptionHandler  = new CustomRestExceptionHandler();

    private Long id = 0L;

    @GetMapping("/users")
    public List<User> getUsersList() {
        log.debug("The user list was successfully retrieved.");
        return new ArrayList<User>(userMap.values());
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        if (user.getName().isBlank() | user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setId(++id);
        userMap.put(user.getId(), user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/users")
    public ResponseEntity<?> update(@RequestBody @Valid @NotNull User user) {
        try {
            if (userMap.get(user.getId()) != null) {
                userMap.put(user.getId(), user);
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } else throw new ValidationException("The User with this id does not exist.");
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
