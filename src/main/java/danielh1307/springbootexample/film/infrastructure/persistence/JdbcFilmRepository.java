package danielh1307.springbootexample.film.infrastructure.persistence;

import danielh1307.springbootexample.film.domain.Film;
import danielh1307.springbootexample.film.domain.FilmId;
import danielh1307.springbootexample.film.domain.FilmRepository;
import danielh1307.springbootexample.film.domain.NoSuchFilmException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static danielh1307.springbootexample.film.domain.Film.newFilm;
import static danielh1307.springbootexample.film.domain.FilmId.filmId;

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
        return this.jdbcTemplate.queryForObject(
                "SELECT id, title, year FROM film where id = :id",
                sqlParameterSource,
                (rs, rowNum) -> newFilm(filmId(rs.getString("id")),
                        rs.getString("title"),
                        rs.getInt("year")));
        // TODO: throw exception if film is not found
    }

    @Override
    public void addFilm(Film film) {
    }
}
