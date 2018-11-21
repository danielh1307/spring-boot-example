package danielh1307.springbootexample.films;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.slf4j.LoggerFactory.getLogger;

// We are using @Controller instead of @RestController here
// @RestController = @Controller + @ResponseBody
// @RestController = API-Calls (JSON etc.)
// @Controller = delivers HTML
// but here we want to deliver a view
@Controller
@RequestMapping(path = "/films")
public class FilmController {

    private static final Logger LOGGER = getLogger(FilmController.class);

    // TODO: add documentation. This only worked when setting properties in application.properties.
    // TODO: check which of these properties we really need and what they mean.
    @GetMapping("/overview")
    public String getFilm(final Model model) {
        model.addAttribute("films", new Film("Pulp Fiction", 1996));
        // this only works if spring-boot-starter-thymeleaf is used
        // otherwise I had 404 errors because there were no associated views
        // I guess spring-boot-starter-thymeleaf configures specific view resolvers so this works
        return "films";
    }

    // TODO: test missing
    // TODO: test ModelAndView, see https://stackoverflow.com/questions/44319070/how-to-access-variables-in-thymeleaf-templates-using-spring-mvc
    // somehow, the data was passed as GET parameters, see also https://www.baeldung.com/spring-redirect-and-forward
    @GetMapping("/filmUploaded")
    public String filmUploaded() {
        return "filmUploaded";
    }

    // TODO: test missing
    @GetMapping("/addFilm")
    public String getFilmForm() {
        return "addFilm";
    }

    // TODO: test missing
    // Be aware to configure spring.servlet.multipart.max-file-size
    @PostMapping("/addFilm")
    public String uploadFilm(FilmForm filmForm, @RequestParam Part cover) throws IOException  {
        LOGGER.info("Added new film: " + filmForm.getTitle() + ", " + filmForm.getYear());
        LOGGER.info("And the content is: " + cover);
        saveFile(cover, filmForm.getTitle() + "-" + filmForm.getYear() + ".jpg");
        return "redirect:filmUploaded";
    }

    private void saveFile(Part part, String filename) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(new File(filename))) {
            InputStream is = part.getInputStream();
            int read = 0;
            final byte[] bytes = new byte[1024];
            while((read = is.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
        }
    }

}
