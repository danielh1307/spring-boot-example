package danielh1307.springbootexample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
// if you want to use method security (@PreAuthorize etc.) you have to set @EnableGlobalMethodSecurity with prePostEnabled=true
// other flags are jsr250Enabled and secureEnabled (both false by default, never use secureEnabled, instead use jsr250Enabled)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig {
}
