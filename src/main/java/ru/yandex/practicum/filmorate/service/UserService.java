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
        return Optional.of(userStorage.addUserInFriendList(id, friendId).orElseThrow()).get();
    }

    public List<User> removeFriend(Long id, Long friendId) {
        return Optional.of(userStorage.removeUserFromFriendList(id, friendId)).orElseThrow().get();
    }

    public List<User> getFriendListByUserId(Long id) {
        return Optional.of(userStorage.getUserFriendList(id).orElseThrow()).get();
    }

    public List<User> getCommonFriendListByUserId(Long id, Long otherUserId) {
        return Optional.of(userStorage.getUserCommonFriendList(id, otherUserId).orElseThrow()).get();
    }

    public User addUser(User user) {
        if (user.getName().isBlank() | user.getName() == null) {
            user.setName(user.getLogin());
        }
        return Optional.of(userStorage.create(user).orElseThrow()).get();
    }

    public User updateUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        return Optional.of(userStorage.update(user).orElseThrow()).get();
    }

    public List<User> getUsers() {
        return Optional.of(userStorage.getUsersList().orElseThrow()).get();
    }

    public User getUserById(Long id) {
        return Optional.of(userStorage.getUserById(id).orElseThrow()).get();
    }
}
