package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public List<User> addNewFriend(Long id, Long friendId) {
        return userStorage.addUserInFriendList(id, friendId);
    }

    public List<User> removeFriend(Long id, Long friendId) {
        return userStorage.removeUserFromFriendList(id, friendId);
    }

    public List<User> getFriendListByUserId(Long id) {
        return userStorage.getUserFriendList(id);
    }

    public List<User> getCommonFriendListByUserId(Long id, Long otherUserId) {
        return userStorage.getUserCommonFriendList(id, otherUserId);
    }

    public User addUser(User user) {
        if (user.getName().isBlank() | user.getName() == null) {
            user.setName(user.getLogin());
        }
        User newUser = new User(user.getName(), user.getLogin(), user.getEmail(), user.getBirthday());
        return userStorage.create(newUser);
    }

    public User updateUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        User newUser = new User(user.getName(), user.getLogin(), user.getEmail(), user.getBirthday());
        return userStorage.update(newUser);
    }

    public List<User> getUsers() {
        if (userStorage.getUsersList() != null) {
            return userStorage.getUsersList();
        } else throw new NullPointerException("Not a single User was found.");
    }

    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }
}
