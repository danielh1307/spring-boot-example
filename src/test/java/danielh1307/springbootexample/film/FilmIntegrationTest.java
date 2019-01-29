package danielh1307.springbootexample.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import danielh1307.springbootexample.film.domain.Film;
import danielh1307.springbootexample.film.infrastructure.web.FilmApiController;
import danielh1307.springbootexample.film.infrastructure.web.FilmRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static danielh1307.springbootexample.film.domain.FilmId.filmId;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

// Test data for this test is loaded in test/resources flyway scripts
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
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
        String filmTitle = this.restTemplate
                // we have to take a real user here
                .withBasicAuth("user", "appl_123")
                .getForObject("/api/films/1/title", String.class);

        // assert
        assertThat(filmTitle, is(equalTo("Pulp Fiction")));
    }

    @Test
    public void getFilm_withExistingFilm_correctFilmIsReturned() throws Exception {
        // act
        String filmString = this.restTemplate
                .withBasicAuth("user", "appl_123")
                .getForObject("/api/films/2", String.class);

        // assert
        Film film = this.objectMapper.readValue(filmString, Film.class);
        assertThat(film.getId(), is(equalTo(filmId("2"))));
        assertThat(film.getTitle(), is(equalTo("Hateful 8")));
        assertThat(film.getYear(), is(equalTo(2014)));
    }

    @Test
    public void getFilm_withNonExistingFilm_404ErrorIsReturned() {
        // act
        ResponseEntity<String> response = this.restTemplate
                .withBasicAuth("user", "appl_123")
                .getForEntity("/api/films/3", String.class);

        // assert
        assertThat(response.getStatusCode(), is(equalTo(NOT_FOUND)));
    }

    @Test
    public void genericRequest_withExistingId_correctFilmIsReturned() throws IOException {
        // act
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        HttpEntity<FilmRequest> request = new HttpEntity<>(new FilmRequest("1"), headers);
        ResponseEntity<String> response = this.restTemplate
                .withBasicAuth("user", "appl_123")
                .postForEntity("/api/films/generic-request", request, String.class);

        // assert
        assertThat(response.getStatusCode(), is(equalTo(OK)));
        Film film = this.objectMapper.readValue(response.getBody(), Film.class);
        assertThat(film.getTitle(), is(equalTo("Pulp Fiction")));
    }

    @Test
    public void addFilm_addNewFilm_filmWasAdded() throws IOException {
        // act
        ResponseEntity<String> response = this.restTemplate
                .withBasicAuth("user", "appl_123")
                .postForEntity("/api/films", new FilmApiController.AddNewFilmRequest("Reservoir Dogs", 1994), String.class);

        // assert
        assertThat(response.getStatusCode(), is(equalTo(OK)));
        Film film = this.objectMapper.readValue(response.getBody(), Film.class);
        assertThat(film.getId(), is(notNullValue()));
        assertThat(film.getTitle(), is(equalTo("Reservoir Dogs")));
        assertThat(film.getYear(), is(equalTo(1994)));
    }
}
