package danielh1307.springbootexample;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
// @ConfigurationProperites supports relaxed binding and generation of metadata
@ConfigurationProperties("environment")
// @Validated can be used to validate properties with JSR 303 (Bean Validation)
// you can put annotations to properties like @Min(10)
// but BeanValidation must be accessible on classpath, e.g. with spring-boot-starter-validation
public class EnvironmentProperties {

    // we need to have a comment in the following format to get a description in spring-configuration-metadata.json
    /**
     * this is a very important property, since it is property 1
     */
    private String prop1;

    /**
     * a little less important than property 1, but still important!
     */
    private String prop2;

    // if the getter is not public, there will be no entry generated for META-INF/spring-configuration-metadata.json
    public String getProp1() {
        return prop1;
    }

    public String getProp2() {
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
