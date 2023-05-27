package ru.yandex.practicum.filmorate.storage.usersdao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getLong("USER_ID"));
        user.setName(rs.getString("USER_NAME"));
        user.setEmail(rs.getString("USER_EMAIL"));
        user.setLogin(rs.getString("USER_LOGIN"));
        user.setBirthday(rs.getDate("USER_BIRTHDAY").toLocalDate());
        return user;
    }
}
