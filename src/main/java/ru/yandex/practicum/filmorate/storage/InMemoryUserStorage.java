package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> userMap = new HashMap<>();
    private Long id = 0L;

    @Override
    public Map<Long, User> getUsersMap() {
        return userMap;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public boolean isUserExist(Long id) {
        return false;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }
}
