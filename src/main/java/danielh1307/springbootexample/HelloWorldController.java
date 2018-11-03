package danielh1307.springbootexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    // by default this property is read from application.properties
    // expression within @Value is SpEL (Spring Expression Language)
    // this is done by registering a PropertySourcesPlaceholderConfigurer
    @Value("${myProperty}")
    private String myProperty;

    @GetMapping("/hello")
    public String hello(@RequestParam final String name) {
        return "Hello, " + name + ", the property is " + myProperty + "\n";
    }
}
