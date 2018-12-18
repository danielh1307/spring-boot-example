package danielh1307.springbootexample.films;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api")
@Api(description = "This is the API controller for film model")
public class FilmApiController {

    private Map<String, Film> filmMap;

    public FilmApiController() {
        filmMap = new HashMap<>();
        filmMap.put("1", new Film("Pulp Fiction", 1994));
        filmMap.put("2", new Film("Hateful 8", 2014));
    }

    @GetMapping("/films/{filmId}/title")
    @ApiOperation("Returns the title of a specific film as String")
    public String getFilmTitle(@PathVariable final String filmId) throws NoSuchFilmException {
        return film(filmId).getTitle();
    }

    // curl http://localhost:8080/api/films/1?mediaType=json ==> returns JSON value
    // curl http://localhost:8080/api/films/1?mediaType=xml ==> returns XML value
    // curl http://localhost:8080/api/films/1?medaiType=csv ==> returns CSV value
    // see WebConfig for the configuration
    @GetMapping(value = "/films/{filmId}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "text/csv"})
    @ApiOperation("Returns the requested film either as JSON, XML or CSV (depending on accept header or parameter mediaType)")
    public Film getFilm(@PathVariable final String filmId) throws NoSuchFilmException {
        return film(filmId);
    }

    // here we can send the request as JSON, thanks to @RequestBody
    // curl -X POST --data '{"number": "1"}' -H "Content-Type: application/json" http://localhost:8080/films/number/request
    @PostMapping(value = "/films/generic-request", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Returns the requested film as JSON")
    public Film genericRequest(@RequestBody final FilmRequest filmRequest) throws NoSuchFilmException {
        return film(filmRequest.getFilmKey());
    }

    private Film film(String filmKey) throws NoSuchFilmException {
        return filmMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(filmKey))
                .map(Map.Entry::getValue).findFirst()
                .orElseThrow(NoSuchFilmException::new);
    }
}
