package danielh1307.springbootexample;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

/**
 * @SpringBootApplication is a composed annotation which combines
 * - @SpringBootConfiguration - specific instance of @Configuration, which indicates Java-based configuration
 * - @EnableAutoConfiguration - parses the dependencies and enables and configures beans based on these dependencies
 * - @ComponentScan - scan for "@-components" (@Service, @Controller, @RestController etc.) (starts in the package of this class and scans all sub-packages).
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // SpringBootApplication.run does the following:
        // - Creates an ApplicationContext
        // - Registry of CommandLinePropertySource (args[] are populated as properties in the Spring environment)
        // - @-components are loaded as beans in context "singleton"
        // - executes all CommandLineRunner beans
        run(Application.class, args);
    }
}
