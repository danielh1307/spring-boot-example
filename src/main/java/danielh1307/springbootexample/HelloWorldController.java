package danielh1307.springbootexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @Controller is a component which is registered by an instance of RequestMappingHandlerMapping
// methods of @Controller are mapped to URLs
// @RestController is annotated with @Controller and @ResponseBody
// @ResponseBody binds the return value directly to the web response body (by using an HttpMessageConverter)
@RestController
public class HelloWorldController {

    // Logging can be configured in resource files
    // we are only using slf4j classes, behind the scenes logback will be used if it is part of the classpath
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

    @GetMapping("/hello")
    public String hello(@RequestParam final String name) {
        LOGGER.info("Hello " + name);
        LOGGER.warn("Just a warning");
        return "Hello, " + name + "\n";
    }
}
