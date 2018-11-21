package danielh1307.springbootexample.films;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
// TODO: check why we need those settings here
@WebMvcTest(
        controllers = FilmController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {FilmsCsvView.class})
)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void csvViewShouldWork() throws Exception {
        mockMvc
                .perform(get("/films/overview").accept("text/csv"))
                .andExpect(status().isOk())
                .andExpect(view().name("films"))
                .andExpect(content().string("Pulp Fiction,1996"));
    }
}
