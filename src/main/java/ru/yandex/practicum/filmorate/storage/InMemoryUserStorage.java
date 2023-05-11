package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
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
    public User create(User user) {
        user.setId(++id);
        userMap.put(user.getId(), user);
        return userMap.get(user.getId());
    }

    @Override
    public User update(User user) {
        userMap.put(user.getId(), user);
        return userMap.get(user.getId());
    }
}
