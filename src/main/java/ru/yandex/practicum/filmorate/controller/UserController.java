package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

////////////////////////////////////////////    GET MAPPING    /////////////////////////////////////////////////////////

    @GetMapping("/users")
    public List<User> getUsersList() {
        log.info("GET all users");
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        log.info("Get a user by id: {}", id);
        return userService.getUserById(id);
    }

    @GetMapping("/users/{id}/friends")
    public ResponseEntity<List<User>> getUserFriendList(@PathVariable Long id) {
        log.info("Get friends by the user Id: {}", id);
        return new ResponseEntity<List<User>>(userService.getFriendListByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getUserCommonFriendList(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Get common friends user id: {}, and user Id: {}", id, otherId);
        return new ResponseEntity<List<User>>(userService.getCommonFriendListByUserId(id, otherId), HttpStatus.OK);
    }

///////////////////////////////////////////    POST MAPPING    /////////////////////////////////////////////////////////

    @PostMapping(value = "/users")
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        log.info("The user with id: {} has been created", user.getId());
        return new ResponseEntity<User>(userService.addUser(user), HttpStatus.OK);
    }

////////////////////////////////////////////    PUT MAPPING    /////////////////////////////////////////////////////////

    @PutMapping(value = "/users")
    public ResponseEntity<User> update(@RequestBody @Valid @NotNull User user) {
        log.info("The user with id: {} has been updated", user.getId());
        return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public ResponseEntity<List<User>> addUserInFriendList(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("The friend with id: {} {} {}", friendId, " has been added to the user with id: ", id);
        return new ResponseEntity<List<User>>(userService.addNewFriend(id, friendId), HttpStatus.OK);
    }

/////////////////////////////////////////    DELETE MAPPING    /////////////////////////////////////////////////////////

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<List<User>> removeUserFromFriendList(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("The friend with id: {} {} {}", friendId, " has been removed from user with id: ", id);
        return new ResponseEntity<List<User>>(userService.removeFriend(id, friendId), HttpStatus.OK);
    }
}