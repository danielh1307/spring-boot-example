package danielh1307.springbootexample.films;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

// We are using @Controller instead of @RestController here
// @RestController = @Controller + @ResponseBody
// but here we want to deliver a view
@Controller
@RequestMapping(path = "/films")
public class FilmController {

    // TODO: add documentation. This only worked when setting properties in application.properties.
    // TODO: check which of these properties we really need and what they mean.
    @GetMapping
    public String getFilm(final Model model) {
        model.addAttribute("films", new Film("Pulp Fiction", 1996));
        // this only works if spring-boot-starter-thymeleaf is used
        // otherwise I had 404 errors because there were no associated views
        // I guess spring-boot-starter-thymeleaf configures specific view resolvers so this works
        return "films";
    }



}
