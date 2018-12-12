package danielh1307.springbootexample.films;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class FilmApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getFilmAsStringShouldWork() throws Exception {
        this.mockMvc
                .perform(get("/api/films/default"))
                .andExpect(status().isOk())
                .andExpect(content().string("Pulp Fiction"));
    }

    @Test
    public void getFilmWithPathVariableShouldWork() throws Exception {
        this.mockMvc
                .perform(get("/api/films/number/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hateful 8"));
    }

    @Test
    public void getUnknownFilmReturns404Error() throws Exception {
        this.mockMvc
                .perform(get("/api/films/number/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFilmAsJsonModelShouldWork() throws Exception {
        // either with strings ...
        this.mockMvc
                .perform(get("/api/films/1"))
                .andExpect(status().isOk())
                // content().json --> the attributes must not be in the same order
                .andExpect(content().json("{\"year\": 1996, \"name\": \"Pulp Fiction\"}"));

        // ... or with objects
        Film expectedResultObject = new Film("Pulp Fiction", 1996);
        this.mockMvc
                .perform(get("/api/films/1"))
                .andExpect(status().isOk())
                // content().json --> the attributes must not be in the same order
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResultObject)));
    }

    @Test
    public void getFilmAsXmlModelShouldWork() throws Exception {
        this.mockMvc
                .perform(get("/api/films/1").param("mediaType", "xml"))
                .andExpect(status().isOk())
                .andExpect(content().xml("<Film><name>Pulp Fiction</name><year>1996</year></Film>"));
    }

    @Test
    public void getFilmWithRequestObjectShouldWork() throws Exception {
        // either with strings ...
        this.mockMvc
                .perform(post("/api/films/number/request")
                        .contentType(APPLICATION_JSON)
                        .content("{\"number\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"year\": 1996, \"name\": \"Pulp Fiction\"}"));

        // ... or with objects
        FilmRequest filmRequest = new FilmRequest(1);
        Film expectedResultObject = new Film("Pulp Fiction", 1996);
        this.mockMvc
                .perform(post("/api/films/number/request")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(filmRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResultObject)));
    }
}
