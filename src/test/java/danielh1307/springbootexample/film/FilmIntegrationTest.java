package danielh1307.springbootexample.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import danielh1307.springbootexample.film.domain.Film;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static danielh1307.springbootexample.film.domain.FilmId.filmId;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

// Test data for this test is loaded in test/resources flyway scripts
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private ObjectMapper objectMapper;

    @Before
    public void initialize() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void getFilmTitle_withExistingFilm_correctTitleIsReturned() {
        // act
        String filmTitle = this.restTemplate.getForObject("/api/films/1/title", String.class);

        // assert
        assertThat(filmTitle, is(equalTo("Pulp Fiction")));
    }

    @Test
    public void getFilm_withExistingFilm_correctFilmIsReturned() throws Exception {
        // act
        String filmString = this.restTemplate.getForObject("/api/films/2", String.class);

        // assert
        Film film = objectMapper.readValue(filmString, Film.class);
        assertThat(film.getId(), is(equalTo(filmId("2"))));
        assertThat(film.getTitle(), is(equalTo("Hateful 8")));
        assertThat(film.getYear(), is(equalTo(2014)));
    }

    @Test
    public void getFilm_withNonExistingFilm_404ErrorIsReturned() throws Exception {
        // act
        ResponseEntity<String> response = this.restTemplate.getForEntity("/api/films/3", String.class);

        // assert
        assertThat(response.getStatusCode(), is(equalTo(NOT_FOUND)));
    }
}
