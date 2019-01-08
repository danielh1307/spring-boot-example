package danielh1307.springbootexample;

import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.slf4j.LoggerFactory.getLogger;

// @Controller is a component which is registered by an instance of RequestMappingHandlerMapping
// methods of @Controller are mapped to URLs
// @RestController is annotated with @Controller and @ResponseBody
// @ResponseBody binds the return value directly to the web response body (by using an HttpMessageConverter)
@RestController
public class HelloWorldController {

    // Logging can be configured in resource files
    // we are only using slf4j classes, behind the scenes logback will be used if it is part of the classpath
    private static final Logger LOGGER = getLogger(HelloWorldController.class);

    // TODO: how to use own user object instead of principal?
    @GetMapping("/hello")
    public String hello(@RequestParam final String name, Principal principal) {
        LOGGER.info("Hello " + name);
        LOGGER.warn("Just a warning");
        String returnString = String.format("java.security.Principal: Hello, %s%n", principal != null ? principal.getName() : "anonymous");
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return returnString + String.format("org.springframework.security.core.userdetails.User: Hello, %s%n", currentUser != null ? currentUser.getUsername() : "anonymous");
    }

    // TODO: test missing
    // user with role ADMIN may call this method
    @GetMapping("/allowed")
    @PreAuthorize("hasRole('ADMIN')")
    public String allowed() {
        return "Allowed!";
    }

    // TODO: test missing
    // only user with role NEVER may call this method
    // remember:
    // 401 - unauthorized --> user is not authenticated
    // 403 - forbidden --> user is authenticated but not authorized
    // besides @PreAuthorize, there are also @PostAuthorize, @PostFilter and @PreFilter
    @GetMapping("/forbidden")
    @PreAuthorize("hasRole('NEVER')")
    public String forbidden() {
        return "Forbidden!";
    }
}
