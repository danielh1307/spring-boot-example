package danielh1307.springbootexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// The annotation @EnableConfigurationProperties registers the class (EnvironmentProperties) as Spring bean.
// Otherwise it would not be found (except you annotate it with e.g. @Component).
// So @EnableConfigurationProperties is an easy way to register a configuration class as Spring bean without annotating it itself.
// See https://stackoverflow.com/questions/49880453/what-difference-does-enableconfigurationproperties-make-if-a-bean-is-already-an
@EnableConfigurationProperties(EnvironmentProperties.class)
public class PropertiesController {

    // by default this property is read from application.properties
    // expression within @Value is SpEL (Spring Expression Language)
    // this is done by registering a PropertySourcesPlaceholderConfigurer
    @Value("${myProperty}")
    private String myProperty;

    private EnvironmentProperties environmentProperties;

    public PropertiesController(EnvironmentProperties environmentProperties) {
        this.environmentProperties = environmentProperties;
    }

    @GetMapping("/singleProperty")
    public String singleProperty() {
        return this.myProperty + "\n";
    }

    @GetMapping("/envProperties")
    public String envProperties() {
        return "prop1 = " + environmentProperties.getProp1() + ", prop2 = " + environmentProperties.getProp2() + "\n";
    }

}
