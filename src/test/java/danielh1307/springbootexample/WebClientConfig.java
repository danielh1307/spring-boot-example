package danielh1307.springbootexample;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import org.springframework.boot.test.web.htmlunit.LocalHostWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Base64;

@Configuration
public class WebClientConfig {

    @Bean(destroyMethod = "close")
    public LocalHostWebClient localHostWebClient(Environment env) {
        LocalHostWebClient localHostWebClient = new LocalHostWebClient(env);
        localHostWebClient.getOptions().setRedirectEnabled(true);
        localHostWebClient.addRequestHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString("user:appl_123".getBytes()));
        DefaultCredentialsProvider userCredentials = new DefaultCredentialsProvider();
        userCredentials.addCredentials("user", "appl_123");
        localHostWebClient.setCredentialsProvider(userCredentials);
        return localHostWebClient;
    }

}
