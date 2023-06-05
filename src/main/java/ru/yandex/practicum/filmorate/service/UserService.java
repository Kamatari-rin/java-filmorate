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
        return userStorage.addUserInFriendList(id, friendId).get();
    }

    public List<User> removeFriend(Long id, Long friendId) {
        return userStorage.removeUserFromFriendList(id, friendId).get();
    }

    public List<User> getFriendListByUserId(Long id) {
        return userStorage.getUserFriendList(id).get();
    }

    public List<User> getCommonFriendListByUserId(Long id, Long otherUserId) {
        return userStorage.getUserCommonFriendList(id, otherUserId).get();
    }

    public User addUser(User user) {
        if (user.getName().isBlank() | user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user).get();
    }

    public User updateUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userStorage.update(user).get();
    }

    public List<User> getUsers() {
        return userStorage.getUsersList().get();
    }

    public User getUserById(Long id) {
        return userStorage.getUserById(id).get();
    }
}
