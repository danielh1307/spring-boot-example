package danielh1307.springbootexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
}
