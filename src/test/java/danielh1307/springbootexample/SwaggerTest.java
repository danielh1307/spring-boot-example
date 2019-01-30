package danielh1307.springbootexample;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;

import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;
import static java.util.Collections.singletonList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SwaggerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void writeSwaggerConfig() throws IOException {
        String swaggerJsonString = this.restTemplate
                .withBasicAuth("user", "appl_123")
                .getForObject("/v2/api-docs", String.class);
        JsonNode swaggerJsonObject = this.objectMapper.readValue(swaggerJsonString, JsonNode.class);

        anonymizeHost(swaggerJsonObject);

        String prettyJson = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(swaggerJsonObject);
        write(get("v2-api-docs.json"), singletonList(prettyJson), Charset.forName("UTF-8"));
    }

    private void anonymizeHost(JsonNode node) {
        ((ObjectNode) node).put("host", "generic-host");
    }
}
