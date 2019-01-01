package danielh1307.springbootexample.film.infrastructure.persistence;

import danielh1307.springbootexample.film.domain.Film;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.junit4.SpringRunner;

import static danielh1307.springbootexample.film.domain.Film.newFilm;
import static danielh1307.springbootexample.film.domain.Film.newFilmWithId;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
// @JdbcTest gives an instance of JdbcTemplate
@JdbcTest
// since with @JdbcTest regular component beans are not loaded to the application context,
// we have to do the component scan to get an instance of JdbcFilmRepository
@ComponentScan(basePackages = "danielh1307.springbootexample.film.infrastructure.persistence")
public class JdbcFilmRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcFilmRepository filmRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void testDatabase_countRows_numberOfRowsCorrect() {
        // assert
        // this is due to the flyway migration script which runs in test mode
        assertThat(getRowsInTableFilm(), is(equalTo(2)));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testDatabase_insertNewRow_numberOfRowsCorrect() {
        // act
        // by default, after each test, a ROLLBACK is done, so this change will not be seen by other tests
        // if you do not want to have this ROLLBACK, add @Rollback(false) to the method
        this.jdbcTemplate.update("INSERT INTO film VALUES ('3', 'Reservoir Dogs', 1992)");

        // assert
        assertThat(getRowsInTableFilm(), is(equalTo(3)));

    }

    @Test
    public void addFilm_addNewFilm_numberOfRowsCorrect() {
        // arrange
        Film reservoirDogs = newFilm("Reservoir Dogs", 1992);

        // act
        this.filmRepository.addFilm(reservoirDogs);

        // assert
        assertThat(getRowsInTableFilm(), is(equalTo(3)));
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", reservoirDogs.getId().getValue());
        this.namedParameterJdbcTemplate.queryForObject("SELECT id, title, year FROM film WHERE id = :id",
                sqlParameterSource,
                (rs, rownum) -> newFilmWithId(rs.getString("id"), rs.getString("title"), rs.getInt("year")));
    }

    private int getRowsInTableFilm() {
        //noinspection ConstantConditions
        return this.jdbcTemplate.queryForObject("SELECT count(*) FROM film", Integer.class);
    }
}
