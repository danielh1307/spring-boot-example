package danielh1307.springbootexample.film.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import danielh1307.springbootexample.film.domain.Film;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static danielh1307.springbootexample.film.domain.Film.newFilm;
import static danielh1307.springbootexample.film.domain.FilmId.filmId;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = FilmApiController.class)
// since we are using other beans (FilmService in this case) we have to perform a @ComponentScan
// alternatively we could use @SpringBootTest (which loads full application context) and @AutoConfigureMockMvc
@ComponentScan(basePackages = "danielh1307.springbootexample.film.boundary")
// This is just a @WebMvcTest so we do not have the full application context.
// Since there is no FilmRepository (needed by FilmService), we are using a "test configuration" for it.
// For a real mock, see FilmServiceTest.
@Import(TestFilmRepository.class)
public class FilmApiControllerWithTestFilmRepositoryTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getFilmTitle_withExistingFilm_correctFilmTitleIsReturned() throws Exception {
        this.mockMvc
                .perform(get("/api/films/2/title"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hateful 8"));
    }

    @Test
    public void getFilmTitle_withMissingFilm_http404IsReturned() throws Exception {
        this.mockMvc
                .perform(get("/api/films/number/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFilm_withExistingFilm_correctJsonIsReturned() throws Exception {
        // either with strings ...
        this.mockMvc
                .perform(get("/api/films/1"))
                .andExpect(status().isOk())
                // content().json --> the attributes must not be in the same order
                .andExpect(content().json("{\"id\":{\"value\":\"1\"},\"title\":\"Pulp Fiction\",\"year\":1994}"));

        // ... or with objects
        Film expectedResultObject = newFilm(filmId("1"),"Pulp Fiction", 1994);
        this.mockMvc
                .perform(get("/api/films/1"))
                .andExpect(status().isOk())
                // content().json --> the attributes must not be in the same order
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResultObject)));
    }

    @Test
    public void getFilm_withExistingFilmAndMediaTypeXml_correctXmlIsReturned() throws Exception {
        this.mockMvc
                .perform(get("/api/films/1").param("mediaType", "xml"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Film><id><value>1</value></id><title>Pulp Fiction</title><year>1994</year></Film>"));
    }

    @Test
    public void getFilm_withExistingFilmAndMediaTypeCsv_correctCsvIsReturned() throws Exception {
        this.mockMvc
                .perform(get("/api/films/2").param("mediaType", "csv"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hateful 8,2014"));
    }

    @Test
    public void getFilm_withExistingFilmAndJsonAcceptHeader_correctJsonIsReturned() throws Exception {
        Film expectedResultObject = newFilm(filmId("1"),"Pulp Fiction", 1994);
        this.mockMvc
                .perform(get("/api/films/1").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResultObject)));
    }

    @Test
    public void getFilm_withExistingFilmAndXmlAcceptHeader_correctXmlIsReturned() throws Exception {
        this.mockMvc
                .perform(get("/api/films/1").accept("application/xml"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Film><id><value>1</value></id><title>Pulp Fiction</title><year>1994</year></Film>"));
    }

    @Test
    public void getFilm_withExistingFilmAndCsvAcceptHeader_correctCsvIsReturned() throws Exception {
        this.mockMvc
                .perform(get("/api/films/2").accept("text/csv"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hateful 8,2014"));
    }

    @Test
    public void getFilm_withMissingFilm_http404IsReturned() throws Exception {
        this.mockMvc
                .perform(get("/api/films/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void genericRequest_withExistingFilmKey_validJsonIsReturned() throws Exception {
        // either with strings ...
        this.mockMvc
                .perform(post("/api/films/generic-request")
                        .contentType(APPLICATION_JSON)
                        .content("{\"filmKey\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":{\"value\":\"1\"},\"title\":\"Pulp Fiction\",\"year\":1994}"));

        // ... or with objects
        FilmRequest filmRequest = new FilmRequest("1");
        Film expectedResultObject = newFilm(filmId("1"),"Pulp Fiction", 1994);
        this.mockMvc
                .perform(post("/api/films/generic-request")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(filmRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResultObject)));
    }

    @Test
    public void genericRequest_withMissingFilmKey_http404IsReturned() throws Exception {
        this.mockMvc
                .perform(post("/api/films/generic-request")
                        .contentType(APPLICATION_JSON)
                        .content("{\"filmKey\": 4}"))
                .andExpect(status().isNotFound());
    }
}
