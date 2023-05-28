package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> getUsersList();

    User create(User user);

    User update(User user);

    User getUserById(Long id);

    List<User> getUserFriendList(Long id);

    List<User> getUserCommonFriendList(Long id, Long friendId);

    List<User> addUserInFriendList(Long id, Long friendId);

    List<User> removeUserFromFriendList(Long id, Long friendId);
}
