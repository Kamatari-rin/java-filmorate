package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {
    Map<Long, User> getUsersMap();
    User getUserById(Long id);
    boolean isUserExist(Long id);
    User create(User user);
    User update(User user);
}
