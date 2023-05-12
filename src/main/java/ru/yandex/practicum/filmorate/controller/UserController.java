package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.AppException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

////////////////////////////////////////////    GET MAPPING    /////////////////////////////////////////////////////////

    @GetMapping("/users")
    public List<User> getUsersList() {
        log.debug("The user list was successfully retrieved.");
        return userService.getUsers();
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getUserFriendList() {
        log.debug("The user list was successfully retrieved.");
        return userService.getUsers();
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getUserCommonFriendList() {
        log.debug("The user list was successfully retrieved.");
        return userService.getUsers();
    }

///////////////////////////////////////////    POST MAPPING    /////////////////////////////////////////////////////////

    @PostMapping(value = "/users")
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        log.debug("The user " + user.getName() + " has been successfully created.");
        return new ResponseEntity<User>(userService.addUser(user), HttpStatus.OK);
    }

////////////////////////////////////////////    PUT MAPPING    /////////////////////////////////////////////////////////

    @PutMapping(value = "/users")
    public ResponseEntity<User> update(@RequestBody @Valid @NotNull User user) {
        try {
            return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public ResponseEntity<User> addUserInFriendList(@PathVariable Long id, @PathVariable Long friendId) {
        try {
            return new ResponseEntity<User>(userService.addNewFriend(id, friendId), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("The Users with this id does not exist: " +
                    "[User id: " + id + "], [Friend id: " + friendId + "].");
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

/////////////////////////////////////////    DELETE MAPPING    /////////////////////////////////////////////////////////

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<User> removeUserFromFriendList(@PathVariable Long id, @PathVariable Long friendId) {
        try {
            return new ResponseEntity<User>(userService.removeFriend(id, friendId), HttpStatus.OK);
        } catch (Exception e) {
            log.warn("The Users with this id does not exist: " +
                    "[User id: " + id + "], [Friend id: " + friendId + "].");
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
