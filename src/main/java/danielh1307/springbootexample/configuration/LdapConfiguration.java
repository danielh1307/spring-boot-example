package danielh1307.springbootexample.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfiguration {

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();

        contextSource.setUrl("ldap://localhost:389");
        contextSource.setBase("dc=example,dc=org");
        contextSource.setUserDn("cn=admin,dc=example,dc=org");
        contextSource.setPassword("admin");

        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

}
