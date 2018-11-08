package danielh1307.springbootexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@ComponentScan
@ActiveProfiles("dev") // use this to test a specific profile
public class DevApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void singlePropertyDev() throws Exception {
        this.mockMvc
                .perform(get("/singleProperty"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DEV")));
    }
}
