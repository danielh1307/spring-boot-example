package danielh1307.springbootexample.films;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

    // we have a HtmlUnit test for this method
    // curl http://localhost:8080/films/overview --> returns HTML
    // ********************* The following is currently not working *************************************
    // curl --header "Accept: text/csv" http://localhost:8080/films/overview --> returns CSV
    // This works because we have the "view" FilmsCsvView which extends AbstractView
    // Please note for this to work we need properties spring.mvc.content-negotiation.media-types.csv and
    // spring.mvc.contentnegotiation.favor-parameter in application.properties
    // **************************************************************************************************
    @GetMapping("/overview")
    public String overview(final Model model) {
        model.addAttribute("films", new Film("Pulp Fiction", 1996));
        // this only works if spring-boot-starter-thymeleaf is used
        // otherwise I had 404 errors because there were no associated views
        // I guess spring-boot-starter-thymeleaf configures specific view resolvers so this works
        return "films";
    }

    @GetMapping("/filmUploaded")
    public String filmUploaded() {
        return "filmUploaded";
    }

    @GetMapping("/addFilm")
    public String getFilmForm() {
        return "addFilm";
    }

    // Be aware to configure spring.servlet.multipart.max-file-size
    @PostMapping("/filmUpload")
    public ModelAndView uploadFilm(FilmForm filmForm, @RequestParam MultipartFile cover, ModelMap modelMap) throws IOException  {
        LOGGER.info("Added new film: " + filmForm.getTitle() + ", " + filmForm.getYear());
        LOGGER.info("And the content is: " + cover);
        String filename = filmForm.getTitle() + "-" + filmForm.getYear() + ".jpg";
        saveFile(cover, filename);
        // this data is passed as GET parameter to the new URL
        // in Thymeleaf, we can access it with ${param.filmname}
        modelMap.addAttribute("filmname", filename);
        return new ModelAndView("redirect:filmUploaded", modelMap);
    }

    private void saveFile(MultipartFile part, String filename) throws IOException {
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
