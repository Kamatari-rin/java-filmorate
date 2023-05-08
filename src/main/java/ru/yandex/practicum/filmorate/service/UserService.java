package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserStorage userStorage;

    public boolean addNewFriendToFriendlist() {
        return true;
    }

    public boolean deleteFriendFromFriendlist() {
        return true;
    }

    public List<Long> getFriendlistByUserId() {
        return new ArrayList<>();
    }

    public List<Long> getCommonFriendlistByuserId() {
        return new ArrayList<>();
    }

    public void isUserExist(Long id) {
        if (!userStorage.isUserExist(id)) {
            throw new NullPointerException("Пользователь с id " + id + "не найден.");
        }
    }

    public Map<Long, User> getUsers() {
        return userStorage.getUsersMap();
    }
}
