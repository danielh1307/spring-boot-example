package danielh1307.springbootexample;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("environment")
// @Validated can be used to validate properties with JSR 303 (Bean Validation)
// you can put annotations to properties like @Min(10)
// but BeanValidation must be accessible on classpath, e.g. with spring-boot-starter-validation
public class EnvironmentProperties {

    private String prop1;

    private String prop2;

    String getProp1() {
        return prop1;
    }

    String getProp2() {
        return prop2;
    }

    // the setter must be public
    // otherwise property is not set
    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }
}
