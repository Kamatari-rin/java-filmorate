package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.ValidationException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserStorage userStorage;

    public User addNewFriend(Long id, Long friendId) {
        isUserExist(id);
        isUserExist(friendId);

        getUserById(id)
                .getFriends()
                .add(friendId);

        return getUserById(id);
    }

    public User removeFriend(Long id, Long friendId) {
        isUserExist(id);
        isUserExist(friendId);

        getUserById(id)
                .getFriends()
                .remove(friendId);

        return getUserById(id);
    }

    public List<Long> getFriendListByUserId(Long id) {
        isUserExist(id);
        return List.copyOf(getUserById(id).getFriends());
    }

    public List<User> getCommonFriendListByUserId(Long id, Long otherUserId) {
        final List<User> commonFriendListByUserId = new ArrayList<>();
        final List<Long> userFriendList = getFriendListByUserId(id);
        final List<Long> otherUserFriendList = getFriendListByUserId(otherUserId);

        for (Long friendId : userFriendList) {
            if (otherUserFriendList.contains(friendId)) {
                commonFriendListByUserId.add(getUserById(friendId));
            }
        }

        return commonFriendListByUserId;
    }

    public User addUser(User user) {
        if (user.getName().isBlank() | user.getName() == null) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    public User updateUser(User user) {
        isUserExist(user.getId());
        return userStorage.update(user);
    }

    public List<User> getUsers() {
        if (userStorage.getUsersMap() != null) {
            return new ArrayList<User>(userStorage.getUsersMap().values());
        } else throw new NullPointerException("Not a single User was found.");
    }

    public User getUserById(Long id) {
        isUserExist(id);
        return userStorage
                .getUsersMap()
                .get(id);
    }

    public void isUserExist(Long id) {
        if (!userStorage.getUsersMap().containsKey(id)) {
            throw new ValidationException("The User with this id does not exist: " +
                    "[User id: " + id + "].");
        }
    }
}
