package danielh1307.springbootexample.configuration;

import org.springframework.context.annotation.Bean;

class LegacyApplicationConfig {

    static class LegacyConfig {
        String name;

        LegacyConfig(String name) {
            this.name = name;
        }
    }

    @Bean
    LegacyConfig legacyConfig() {
        return new LegacyConfig("I am a legacy config");
    }
}
