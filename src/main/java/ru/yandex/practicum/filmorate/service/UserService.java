package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserStorage userStorage;

    boolean addNewFriendToFriendlist() {
        return true;
    }
    boolean deleteFriendFromFriendlist() {
        return true;
    }
    List<Long> getFriendlistByUserId() {
        return new ArrayList<>();
    }
    List<Long> getCommonFriendlistByuserId() {
        return new ArrayList<>();
    }
    void isUserExist(Long id) {
        if (!userStorage.isUserExist(id)) {
            throw new NullPointerException("Пользователь с id " + id + "не найден.");
        }
    }
}
