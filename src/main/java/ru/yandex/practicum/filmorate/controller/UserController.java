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
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/{id}/friends")
    public ResponseEntity<List<User>> getUserFriendList(@PathVariable Long id) {
        return new ResponseEntity<List<User>>(userService.getFriendListByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getUserCommonFriendList(@PathVariable Long id, @PathVariable Long otherId) {
        return new ResponseEntity<List<User>>(userService.getCommonFriendListByUserId(id, otherId), HttpStatus.OK);
    }

///////////////////////////////////////////    POST MAPPING    /////////////////////////////////////////////////////////

    @PostMapping(value = "/users")
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        return new ResponseEntity<User>(userService.addUser(user), HttpStatus.OK);
    }

////////////////////////////////////////////    PUT MAPPING    /////////////////////////////////////////////////////////

    @PutMapping(value = "/users")
    public ResponseEntity<User> update(@RequestBody @Valid @NotNull User user) {
        return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public ResponseEntity<User> addUserInFriendList(@PathVariable Long id, @PathVariable Long friendId) {
        return new ResponseEntity<User>(userService.addNewFriend(id, friendId), HttpStatus.OK);
    }

/////////////////////////////////////////    DELETE MAPPING    /////////////////////////////////////////////////////////

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<User> removeUserFromFriendList(@PathVariable Long id, @PathVariable Long friendId) {
        return new ResponseEntity<User>(userService.removeFriend(id, friendId), HttpStatus.OK);
    }
}
