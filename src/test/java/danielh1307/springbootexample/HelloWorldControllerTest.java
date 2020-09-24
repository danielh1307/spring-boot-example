package danielh1307.springbootexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.System.lineSeparator;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
public class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void helloWorldShouldWork() throws Exception {
        // we can take any user here - it must not be the one which is really configured
        String expectedString = "java.security.Principal: Hello, some_user" + lineSeparator() +
                "org.springframework.security.core.userdetails.User: Hello, some_user" + lineSeparator();

        this.mockMvc
                .perform(get("/hello").param("name", "World").with(user("some_user")))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedString));
    }
}
