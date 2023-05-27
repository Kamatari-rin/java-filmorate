package ru.yandex.practicum.filmorate.storage.usersdao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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
    public List<User> getUsersList() {
        return  jdbcTemplate.query("select * from USERS", userRowMapper);
    }

    @Override
    public User create(User user) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("USER_EMAIL", user.getEmail());
        parameters.put("USER_LOGIN", user.getLogin());
        parameters.put("USER_NAME", user.getName());
        parameters.put("USER_BIRTHDAY", user.getBirthday());

        Long userID = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return getUserById(userID);
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update("update USERS "
                              + "set    USER_EMAIL = ?, "
                              + "       USER_LOGIN = ?, "
                              + "       USER_NAME = ?, "
                              + "       USER_BIRTHDAY = ? "
                              + "where  USER_ID = ?",
                                 user.getEmail(),
                                 user.getLogin(),
                                 user.getName(),
                                 user.getBirthday(),
                                 user.getId());

        return getUserById(user.getId());
    }

    @Override
    public User getUserById(Long id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * "
                                                           + "from  USERS "
                                                           + "where USER_ID = ?", id);

        if (userRows.next()) {
            User user = new User(
                    userRows.getLong(  "USER_ID"),
                    userRows.getString("USER_EMAIL"),
                    userRows.getString("USER_LOGIN"),
                    userRows.getString("USER_NAME"),
                    userRows.getDate(  "USER_BIRTHDAY").toLocalDate());

            return user;
        } else {
            log.warn("Пользователь с идентификатором {} не найден.", id);
            return null;
        }
    }

    @Override
    public List<User> getUserFriendList(Long id) {
        return jdbcTemplate.query("select * "
                                    + "from  USERS "
                                    + "where USER_ID in (select  FRIEND_ID "
                                                      + "from  FRIENDS "
                                                      + "where USER_ID = ?)", userRowMapper, id);
    }

    @Override
    public List<User> getUserCommonFriendList(Long id, Long friendId) {
        return jdbcTemplate.query("select * "
                                    + "from  USERS "
                                    + "where USER_ID in (select FRIEND_ID "
                                                      + "from   FRIENDS "
                                                      + "where  USER_ID = ?) "
                                    + "and   USER_ID in (select FRIEND_ID "
                                                      + "from   FRIENDS "
                                                      + "where  USER_ID = ?)", userRowMapper, id, friendId);
    }

    @Override
    public List<User> addUserInFriendList(Long id, Long friendId) {
        jdbcTemplate.update("insert "
                              + "into FRIENDS (USER_ID, FRIEND_ID, STATUS) "
                              + "values (?, ?, ?)", id, friendId, "");

        return getUserFriendList(id);
    }

    @Override
    public List<User> removeUserFromFriendList(Long id, Long friendId) {
        jdbcTemplate.update("delete "
                              + "from  FRIENDS "
                              + "where USER_ID = ? "
                              + "and   FRIEND_ID = ? ", id, friendId);

        return getUserFriendList(id);
    }
}