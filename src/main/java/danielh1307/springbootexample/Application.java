package danielh1307.springbootexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication is a composed annotation which combines
 * - @SpringBootConfiguration - specific instance of @Configuration, which indicates Java-based configuration
 * - @EnableAutoConfiguration - parses the dependencies and enables and configures beans based on these dependencies
 * - @ComponentScan - scan for "@-components" (@Service, @Controller, @RestController etc.) (starts in the package of this class and scans all sub-packages).
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //      easiest way: SpringApplication.run(Application.class, args);
        // SpringBootApplication.run does the following:
        // - Creates an ApplicationContext
        // - Registry of CommandLinePropertySource (args[] are populated as properties in the Spring environment)
        // - @-components are loaded as beans in context "singleton"
        // - executes all CommandLineRunner beans

        // if you want to change settings of the application, use this way:
        SpringApplication springApplication = new SpringApplication(Application.class);
        disableCommandLineProperties(springApplication);
        springApplication.run(args);
    }

    private static void disableCommandLineProperties(SpringApplication springApplication) {
        // true is the default here
        // if set to false, you cannot pass command line properties from outside
        // e.g. java -jar target/spring-boot-example.jar --myProperty="different value" is not working any more
        // but what is still working is pass it to spring-boot-plugin: ./mvnw spring-boot:run -DmyProperty="different value"
        springApplication.setAddCommandLineProperties(false);
    }
}