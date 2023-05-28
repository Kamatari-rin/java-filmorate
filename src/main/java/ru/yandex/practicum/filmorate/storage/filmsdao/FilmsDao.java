package ru.yandex.practicum.filmorate.storage.filmsdao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.sql.DataSource;
import java.util.*;

@Slf4j
@Repository
public class FilmsDao implements FilmStorage {

    private JdbcTemplate jdbcTemplate;
    private final FilmRowMapper filmRowMapper;
    private final GenreRowMapper genreRowMapper;
    private final MPARatingRowMapper mpaRatingRowMapper;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public FilmsDao(JdbcTemplate jdbcTemplate,
                    DataSource dataSource,
                    FilmRowMapper filmRowMapper,
                    GenreRowMapper genreRowMapper,
                    MPARatingRowMapper mpaRatingRowMapper) {

        this.jdbcTemplate = jdbcTemplate;
        this.filmRowMapper = filmRowMapper;
        this.genreRowMapper = genreRowMapper;
        this.mpaRatingRowMapper = mpaRatingRowMapper;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                                    .withTableName("FILMS")
                                    .usingGeneratedKeyColumns("FILM_ID");
    }

////////////////////////////////////////////    ADD and UPDATE FILMS    ////////////////////////////////////////////////

    @Override
    public Optional<Film> addFilm(Film film) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("FILM_NAME", film.getName());
        parameters.put("FILM_DESCRIPTION", film.getDescription());
        parameters.put("FILM_DURATION", film.getDuration());
        parameters.put("FILM_RELEASE_DATE", film.getReleaseDate());
        parameters.put("RATING_ID", film.getMpa().getId());

        Long filmId = simpleJdbcInsert
                        .executeAndReturnKey(parameters)
                        .longValue();

        updateFilmGenres(film, filmId);

        return getFilmByID(filmId);
    }

    @Override
    public Optional<Film> updateFilm(Film film) {
        jdbcTemplate.update("update FILMS "
                              + "set    FILM_NAME = ?, "
                              + "       FILM_DESCRIPTION = ?, "
                              + "       FILM_DURATION = ?, "
                              + "       FILM_RELEASE_DATE = ? "
                              + "       RATING_ID = ? "
                              + "where  FILM_ID = ?",
                        film.getName(),
                        film.getDescription(),
                        film.getDuration(),
                        film.getReleaseDate(),
                        film.getMpa().getName(),
                        film.getId());

        updateFilmGenres(film, film.getId());

        return getFilmByID(film.getId());
    }

    private void updateFilmGenres(Film film, Long filmId) {
        Set<Genre> filmGenres = film.getGenres();

        for (Genre genre : filmGenres) {
            Long genreID = genre.getId();
            jdbcTemplate.update("insert "
                                  + "into GENRE_FILM (FILM_ID, GENRE_ID) "
                                  + "values (?, ?) ", filmId, genreID);
        }
    }

///////////////////////////////////////////////    GET FILMS    ////////////////////////////////////////////////////////

    @Override
    public Optional<Film> getFilmByID(Long id) {
        Film film = jdbcTemplate.queryForObject("select * "
                                                  + "from  FILMS "
                                                  + "where FILM_ID = ?", filmRowMapper, id);

        Set<Long> usersLike = Set.copyOf(jdbcTemplate.queryForList("select USER_ID "
                                                                     + "from   FILM_LIKE "
                                                                     + "where  FILM_ID = ?", Long.class, id));

        Set<Genre> filmGenres = Set.copyOf(jdbcTemplate.query("select gf.GENRE_ID,  ge.GENRE_NAME "
                                                                + "from   GENRE_FILM as gf "
                                                                + "left join GENRES as ge on gf.GENRE_ID = ge.GENRE_ID "
                                                                + "group by gf.GENRE_ID, ge.GENRE_NAME "
                                                                + "having FILM_ID = ? ", genreRowMapper, id));

        log.warn(filmGenres.toString());

        MpaRating rating = jdbcTemplate.queryForObject("select * "
                                                         + "from  MPA_RATINGS "
                                                         + "where RATING_ID = ? ",
                                                         mpaRatingRowMapper, film.getMpa().getId());

        film.setLikes(usersLike);
        film.setGenres(filmGenres);
        film.setMpa(rating);

        return Optional.of(film);
    }

    @Override
    public Optional<List<Film>> getFilmsList() {
        return Optional.of(jdbcTemplate.query("select * from FILMS", filmRowMapper));
    }

    @Override
    public Optional<List<Film>> getPopularFilmsList(int count) {
        return null;
    }

///////////////////////////////////////////////    GENRES    ///////////////////////////////////////////////////////////

    @Override
    public Optional<List<Genre>> getAllGenres() {
        return Optional.of(jdbcTemplate.query("select * from GENRES order by GENRE_ID ", genreRowMapper));
    }

    @Override
    public Optional<Genre> getGenreByID(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * "
                                                                 + "from  GENRES "
                                                                 + "where GENRE_ID = ? "
                                                                 + "order by GENRE_ID", genreRowMapper, id));
    }

///////////////////////////////////////////////    MPA RATING    ///////////////////////////////////////////////////////

    @Override
    public Optional<List<MpaRating>> getAllMpaRatings() {
        return Optional.of(jdbcTemplate.query("select * "
                                                + "from MPA_RATINGS", mpaRatingRowMapper));
    }

    @Override
    public Optional<MpaRating> getMpaRatingById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * "
                                                                 + "from  MPA_RATINGS "
                                                                 + "where RATING_ID = ?", mpaRatingRowMapper, id));
    }

///////////////////////////////////////////////    USERS LIKES    //////////////////////////////////////////////////////

    @Override
    public Optional<Film> likeFilm(Long filmId, Long userId) {
        jdbcTemplate.update("insert "
                              + "into FILM_LIKE (USER_ID, FILM_ID) "
                              + "values (?, ?) "
                              + "ON CONFLICT DO NOTHING;", userId, filmId);

        return getFilmByID(filmId);
    }

    @Override
    public Optional<Film> removeUserLikeFromFilm(Long filmId, Long userId) {
        jdbcTemplate.update("delete "
                              + "from  FILM_LIKE "
                              + "where USER_ID = ? "
                              + "and   FILM_ID = ? ", userId, filmId);

        return getFilmByID(filmId);
    }

///////////////////////////////////////////////    SERVICE METHODS    //////////////////////////////////////////////////

    private void setGenresAndRatings() {
        jdbcTemplate.update("insert "
                              + "into GENRES (GENRE_ID, GENRE_NAME) "
                              + "values (1, \"Комедия\"), "
                                     + "(2, \"Драма\"), "
                                     + "(3, \"Мультфильм\"), "
                                     + "(4, \"Триллер\"), "
                                     + "(5, \"Документальный\"), "
                                     + "(6, \"Боевик\") ");

        jdbcTemplate.update("insert "
                              + "into MPA_RATINGS (RATING_ID, RATING_NAME) "
                              + "values (1, \"G\"), "
                                     + "(2, \"PG\"), "
                                     + "(3, \"PG-13\"), "
                                     + "(4, \"R\"), "
                                     + "(5, \"NC-17\"), ");
    }
}
