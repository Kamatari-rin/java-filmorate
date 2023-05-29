package ru.yandex.practicum.filmorate.storage.usersdao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.sql.DataSource;
import java.util.*;

@Slf4j
@Repository
public class UsersDao implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final UserRowMapper userRowMapper;

    @Autowired
    public UsersDao(JdbcTemplate jdbcTemplate, DataSource dataSource, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("USER_ID");
    }

    @Override
    public Optional<List<User>> getUsersList() {
        return  Optional.of(jdbcTemplate.query("select * from USERS order by USER_ID", userRowMapper));
    }

    @Override
    public Optional<User> create(User user) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("USER_EMAIL", user.getEmail());
        parameters.put("USER_LOGIN", user.getLogin());
        parameters.put("USER_NAME", user.getName());
        parameters.put("USER_BIRTHDAY", user.getBirthday());

        Long userID = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        log.warn(getUserById(userID).toString());
        return getUserById(userID);
    }

    @Override
    public Optional<User> update(User user) {
        Long id = user.getId();
        log.warn(user.toString());
        jdbcTemplate.update("update USERS "
                              + "set    USER_EMAIL = ?, "
                                     + "USER_LOGIN = ?, "
                                     + "USER_NAME = ?, "
                                     + "USER_BIRTHDAY = ? "
                              + "where  USER_ID = ?",
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());

        return getUserById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * "
                                                                 + "from  USERS "
                                                                 + "where USER_ID = ?", userRowMapper, id));
    }

    @Override
    public Optional<List<User>> getUserFriendList(Long id) {
        return Optional.of(jdbcTemplate.query("select * "
                                    + "from  USERS "
                                    + "where USER_ID in (select  FRIEND_ID "
                                                      + "from  FRIENDS "
                                                      + "where USER_ID = ?)", userRowMapper, id));
    }

    @Override
    public Optional<List<User>> getUserCommonFriendList(Long id, Long friendId) {
        return Optional.of(jdbcTemplate.query("select * "
                                    + "from  USERS "
                                    + "where USER_ID in (select FRIEND_ID "
                                                      + "from   FRIENDS "
                                                      + "where  USER_ID = ?) "
                                    + "and   USER_ID in (select FRIEND_ID "
                                                      + "from   FRIENDS "
                                                      + "where  USER_ID = ?)", userRowMapper, id, friendId));
    }

    @Override
    public Optional<List<User>> addUserInFriendList(Long id, Long friendId) {
        jdbcTemplate.update("insert "
                              + "into FRIENDS (USER_ID, FRIEND_ID) "
                              + "values (?, ?)", id, friendId);

        return getUserFriendList(id);
    }

    @Override
    public Optional<List<User>> removeUserFromFriendList(Long id, Long friendId) {
        jdbcTemplate.update("delete "
                              + "from  FRIENDS "
                              + "where USER_ID = ? "
                              + "and   FRIEND_ID = ? ", id, friendId);

        return getUserFriendList(id);
    }
}