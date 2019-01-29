package danielh1307.springbootexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PropertiesController.class)
@TestPropertySource(locations = "classpath:test.properties")
public class PropertiesApplicationTestPropertiesTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void singleProperty() throws Exception {
        this.mockMvc
                .perform(get("/singleProperty").with(user("some_user")))
                .andExpect(status().isOk())
                .andExpect(content().string("test value" + "\n"));
    }
}
