package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    Optional<List<User>> getUsersList();

    Optional<User> create(User user);

    Optional<User> update(User user);

    Optional<User> getUserById(Long id);

    Optional<List<User>> getUserFriendList(Long id);

    Optional<List<User>> getUserCommonFriendList(Long id, Long friendId);

    Optional<List<User>> addUserInFriendList(Long id, Long friendId);

    Optional<List<User>> removeUserFromFriendList(Long id, Long friendId);
}
