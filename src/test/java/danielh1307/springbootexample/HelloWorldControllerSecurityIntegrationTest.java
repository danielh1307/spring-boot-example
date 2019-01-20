package danielh1307.springbootexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HelloWorldControllerSecurityIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void allowed_unauthenticatedUser_isAllowedToCallEndpoint() {
        // act
        String returnValue = this.restTemplate.getForObject("/allowed", String.class);

        // assert
        assertThat(returnValue, is(equalTo("I was called!")));
    }

    @Test
    public void hello_unauthenticatedUser_isUnauthorizedToCallEndpoint() {
        // act
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("/hello", String.class);

        // assert
        assertThat(responseEntity.getStatusCode(), is(equalTo(UNAUTHORIZED)));
    }

    @Test
    public void hello_userUser_isAllowedToCallEndpoint() {
        // act
        ResponseEntity<String> responseEntity = this.restTemplate
                .withBasicAuth("user", "appl_123")
                .getForEntity("/hello?name=user", String.class);

        // assert
        assertThat(responseEntity.getStatusCode(), is(equalTo(OK)));
    }

    @Test
    public void hello_unknownUser_isUnauthorizedToCallEndpoint() {
        // act
        ResponseEntity<String> responseEntity = this.restTemplate
                .withBasicAuth("unknown", "unknown")
                .getForEntity("/hello", String.class);

        // assert
        assertThat(responseEntity.getStatusCode(), is(equalTo(UNAUTHORIZED)));
    }

    @Test
    public void allowedForAdmin_adminUser_isAllowedToCallEndpoint() {
        // act
        String returnValue = this.restTemplate
                .withBasicAuth("admin", "admin")
                .getForObject("/allowedForAdmin", String.class);

        // assert
        assertThat(returnValue, is(equalTo("Allowed!")));
    }

    @Test
    public void allowedForAdmin_userUser_isForbiddenToCallEndpoint() {
        // act
        ResponseEntity<String> responseEntity = this.restTemplate
                .withBasicAuth("user", "appl_123")
                .getForEntity("/allowedForAdmin", String.class);

        // assert
        assertThat(responseEntity.getStatusCode(), is(equalTo(FORBIDDEN)));
    }

    @Test
    public void forbidden_adminUser_isFobiddenToCallEndpoint() {
        // act
        ResponseEntity<String> responseEntity = this.restTemplate
                .withBasicAuth("admin", "admin")
                .getForEntity("/forbidden", String.class);

        // assert
        assertThat(responseEntity.getStatusCode(), is(equalTo(FORBIDDEN)));
    }


}
