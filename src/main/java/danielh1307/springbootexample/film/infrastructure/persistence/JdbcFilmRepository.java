package danielh1307.springbootexample.film.infrastructure.persistence;

import danielh1307.springbootexample.film.domain.Film;
import danielh1307.springbootexample.film.domain.FilmId;
import danielh1307.springbootexample.film.domain.FilmRepository;
import danielh1307.springbootexample.film.domain.NoSuchFilmException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static danielh1307.springbootexample.film.infrastructure.support.FilmCreator.createFilm;

// @Repository translates checked (infrastructure) exceptions to runtime exceptions
@Repository
// TODO: check this annotation
@Transactional
public class JdbcFilmRepository implements FilmRepository {

    // if spring-boot-starter-jdbc is used, we automatically have this template
    // (as well as NamedParameterJdbcTemplate)
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcFilmRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Film getFilm(FilmId filmId) throws NoSuchFilmException {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", filmId.getValue());
        try {
            return this.jdbcTemplate.queryForObject(
                    "SELECT id, title, year FROM film where id = :id",
                    sqlParameterSource,
                    // we are not calling the constructor here since this should only be done in the Service
                    (rs, rowNum) -> createFilm(rs.getString("id"),
                            rs.getString("title"),
                            rs.getInt("year")));
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchFilmException();
        }
    }

    @Override
    public void addFilm(Film film) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", film.getId().getValue());
        sqlParameterSource.addValue("title", film.getTitle());
        sqlParameterSource.addValue("year", film.getYear());
        this.jdbcTemplate.update("INSERT INTO film VALUES (:id, :title, :year)", sqlParameterSource);
    }
}
