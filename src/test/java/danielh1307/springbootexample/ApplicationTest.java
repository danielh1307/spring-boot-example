package danielh1307.springbootexample;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // use specific SpringRunner to run the tests
@WebMvcTest // only Spring MVC components are tested
@ComponentScan // this is needed since we are wiring components in the classes to test (e.g. PropertiesController)
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

    @Test
    public void singleProperty() throws Exception {
        this.mockMvc
                .perform(get("/singleProperty"))
                .andExpect(status().isOk())
                .andExpect(content().string("original value" + "\n"));
    }

    @Test
    public void envProperties() throws Exception {
        this.mockMvc
                .perform(get("/envProperties"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("first property")))
                .andExpect(content().string(containsString("second property")));
    }
}
