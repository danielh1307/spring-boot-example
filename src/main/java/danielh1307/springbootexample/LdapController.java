package danielh1307.springbootexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Name;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@RestController
public class LdapController {

    @Autowired
    private LdapTemplate ldapTemplate;

    @GetMapping("/ldap")
    public String getLdap() {
        // Works if no base is specified
//        Name dn = LdapNameBuilder.newInstance().add("dc", "org").add("dc", "example").add("cn", "Hugo Hase").build();

        // Works if base is specified
        Name dn = LdapNameBuilder.newInstance().add("cn", "Hugo Hase").build();
        DirContextOperations context = this.ldapTemplate.lookupContext(dn);
        String uid = context.getStringAttribute("uid");
        String password = new String((byte[]) context.getObjectAttribute("userPassword"));

        // Works
        LdapQuery query = query().where("uid").is("Hugo.Hase");
        List<String> cn = this.ldapTemplate.search(
                query,
                (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());

        // change password
        context.setAttributeValue("userPassword", "{SHA}passwort2");
        this.ldapTemplate.modifyAttributes(context);

        return password;
    }
}
