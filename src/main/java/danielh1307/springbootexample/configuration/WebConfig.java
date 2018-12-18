package danielh1307.springbootexample.configuration;

import danielh1307.springbootexample.films.FilmCsvHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
@EnableWebMvc // this annotation disables automatic configuration of WebMVC
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private FilmCsvHttpMessageConverter filmCsvHttpMessageConverter;

    // use this setup for
    // ?mediaType=xml / --header "Accept: application/xml" ==> XML
    // ?mediaType=json / --header "Accept: application/json" ==> JSON
    // ?mediaType=csv / --header "Accept: text/csv" ==> CSV
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {
        contentNegotiationConfigurer
                .favorPathExtension(false)
                .favorParameter(true)
                .parameterName("mediaType")
                .defaultContentType(APPLICATION_JSON)
                .mediaType("csv", new MediaType("text", "csv"));
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

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(this.filmCsvHttpMessageConverter);
    }
}
