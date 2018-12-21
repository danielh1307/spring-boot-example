package danielh1307.springbootexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // use specific SpringRunner to run the tests
// @WebMvcTest only scans the defined controller and the MVC infrastructure
// if there are other dependencies to test, we have to load the config or provide a Mock
// this is a "slice" of the application since only the controller is tested --> tests are faster
// than starting a full application context
// @SpringBootTest in contrary loads the full application context
@WebMvcTest(controllers = HelloWorldController.class) // only Spring MVC components are tested
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void helloWorldShouldWork() throws Exception {
        this.mockMvc
                .perform(get("/hello").param("name", "World"))
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("Hello, World")));
    }
}
