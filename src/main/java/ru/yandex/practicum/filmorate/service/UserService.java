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

    public User addNewFriend(Long id, Long friendId) {
        isUserExist(id);
        isUserExist(friendId);

        getUserById(id)
                .getFriends()
                .add(friendId);

        getUserById(friendId)
                .getFriends()
                .add(id);

        return getUserById(id);
    }

    public User removeFriend(Long id, Long friendId) {
        isUserExist(id);
        isUserExist(friendId);

        getUserById(id)
                .getFriends()
                .remove(friendId);

        getUserById(friendId)
                .getFriends()
                .remove(id);

        return getUserById(id);
    }

    public List<User> getFriendListByUserId(Long id) {
        isUserExist(id);

        final List<User> friendList = new ArrayList<>();
        final List<Long> userFriendList = List.copyOf(getUserById(id).getFriends());

        for (Long friendId : userFriendList) {
            friendList.add(getUserById(friendId));
        }
        return friendList;
    }

    public List<User> getCommonFriendListByUserId(Long id, Long otherUserId) {
        isUserExist(id);
        isUserExist(otherUserId);

        final List<User> commonFriendListByUserId = new ArrayList<>();
        final List<Long> userFriendList = List.copyOf(getUserById(id).getFriends());
        final List<Long> otherUserFriendList = List.copyOf(getUserById(otherUserId).getFriends());

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
            throw new NullPointerException("The User with this id does not exist: " +
                    "[User id: " + id + "].");
        }
    }
}
