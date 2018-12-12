package danielh1307.springbootexample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;

@Configuration
// the following annotation is IMPORTANT here if the imported Spring MVC configuration has to be changed
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // use this setup for
    // ?mediaType=xml ==> XML
    // ?mediaType=json ==> JSON
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {
        contentNegotiationConfigurer
                .favorPathExtension(false)
                .favorParameter(true)
                .parameterName("mediaType")
                .ignoreAcceptHeader(true)
                .useRegisteredExtensionsOnly(true)
                .defaultContentType(APPLICATION_JSON)
                .mediaType("xml", APPLICATION_XML)
                .mediaType("json", APPLICATION_JSON);
    }

    // use this setup for
    // .xml ==> XML
    // .json ==> JSON
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {
//        contentNegotiationConfigurer
//                .ignoreAcceptHeader(true)
//                .useRegisteredExtensionsOnly(true)
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                .mediaType("xml", MediaType.APPLICATION_XML)
//                .mediaType("json", MediaType.APPLICATION_JSON);
//    }

    // use this setup for
    // --header "Accept: application/xml" ==> XML
    // --header "Accept: application/json" ==> JSON
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {
//        contentNegotiationConfigurer
//                .ignoreAcceptHeader(false)
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                .mediaType("xml", MediaType.APPLICATION_XML)
//                .mediaType("json", MediaType.APPLICATION_JSON);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
