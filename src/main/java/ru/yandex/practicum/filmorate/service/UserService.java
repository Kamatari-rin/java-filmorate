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
                .add(getUserById(friendId));

        return getUserById(id);
    }

    public User removeFriend(Long id, Long friendId) {
        isUserExist(id);
        isUserExist(friendId);

        getUserById(id)
                .getFriends()
                .remove(getUserById(friendId));

        return getUserById(id);
    }

    public List<User> getFriendListByUserId(Long id) {
        isUserExist(id);

//        final List<User> friendList = new ArrayList<>();
//        final List<Long> userFriendList = List.copyOf(getUserById(id).getFriends());
//
//        for (Long friendId : userFriendList) {
//            friendList.add(getUserById(friendId));
//        }
        return List.copyOf(getUserById(id).getFriends());
    }

    public List<User> getCommonFriendListByUserId(Long id, Long otherUserId) {
        final List<User> commonFriendListByUserId = new ArrayList<>();
        final List<User> userFriendList = List.copyOf(getUserById(id).getFriends());
        final List<User> otherUserFriendList = List.copyOf(getUserById(otherUserId).getFriends());

        for (User user : userFriendList) {
            if (otherUserFriendList.contains(user)) {
                commonFriendListByUserId.add(user);
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
