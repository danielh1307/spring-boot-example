package danielh1307.springbootexample.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration // this indicates a Java Configuration class
@Import(LegacyApplicationConfig.class) // this is how you can import configuration without having to annotate the class itself
class ApplicationConfig {

    static class FooService {
        final String name;

        FooService(String name) {
            this.name = name;
        }
    }

    static class BarService {
        final String name;
        final FooService fooService;

        BarService(String name, FooService fooService) {
            this.name = name;
            this.fooService = fooService;
        }
    }

    // @Bean annotation is essential - only with this annotation the component is correctly configured
    // by default, the bean gets the same name as the method which created it (see BeanNameGenerator for this) (or the class name with small starting letter)
    // this can be overridden with name (as in this example)
    @Bean(name = "fooService")
    FooService myFooService() {
        return new FooService("I am a Foo service");
    }

    @Bean
    BarService barServiceSpecialName(FooService fooService) {
        return new BarService("I am a Bar service", fooService);
    }


}