package danielh1307.springbootexample.film.infrastructure.persistence;

import danielh1307.springbootexample.film.domain.NoSuchFilmException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testDatabase_countRows_numberOfRowsCorrect() {
        // assert
        // this is due to the flyway migration script which runs in test mode
        assertThat(getRowsInTableFilm(), is(equalTo(2)));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testDatabase_insertNewRow_numberOfRowsCorrect() throws NoSuchFilmException {
        // act
        // by default, after each test, a ROLLBACK is done, so this change will not be seen by other tests
        // if you do not want to have this ROLLBACK, add @Rollback(false) to the method
        this.jdbcTemplate.update("INSERT INTO film VALUES ('3', 'Reservoir Dogs', 1980)");

        // assert
        assertThat(getRowsInTableFilm(), is(equalTo(3)));

    }

    private int getRowsInTableFilm() {
        //noinspection ConstantConditions
        return this.jdbcTemplate.queryForObject("SELECT count(*) FROM film", Integer.class);
    }
}
