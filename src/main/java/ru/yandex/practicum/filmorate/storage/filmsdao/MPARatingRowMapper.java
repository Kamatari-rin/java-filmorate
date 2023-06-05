package ru.yandex.practicum.filmorate.storage.filmsdao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MPARatingRowMapper implements RowMapper<MpaRating> {

    @Override
    public MpaRating mapRow(ResultSet rs, int rowNum) throws SQLException {
        MpaRating mpaRating = new MpaRating();
        mpaRating.setId(rs.getLong("RATING_ID"));
        mpaRating.setName(rs.getString("RATING_NAME"));
        return mpaRating;
    }
}
