package ru.yandex.practicum.filmorate.storage.filmsdao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();

        film.setId(rs.getLong("FILM_ID"));
        film.setName(rs.getString("FILM_NAME"));
        film.setDescription(rs.getString("FILM_DESCRIPTION"));
        film.setReleaseDate(rs.getDate("FILM_RELEASE_DATE").toLocalDate());
        film.setDuration(rs.getInt("FILM_DURATION"));

        MpaRating rating = new MpaRating();
        rating.setId(rs.getLong("RATING_ID"));

        film.setMpa(rating);

        return film;
    }
}
