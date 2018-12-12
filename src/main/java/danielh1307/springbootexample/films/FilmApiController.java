package danielh1307.springbootexample.films;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/films")
@Api(description = "This is the API controller for film model")
public class FilmApiController {

    @GetMapping("/default")
    @ApiOperation("Returns just a single film (the default one) as string")
    public String getFilmAsString() {
        return "Pulp Fiction";
    }

    @GetMapping("/number/{myNumber}")
    public String getFilmWithPathVariable(@PathVariable final int myNumber) throws NoSuchFilmException {
        switch (myNumber) {
            case 1: return "Pulp Fiction";
            case 2: return "Hateful 8";
            default: throw new NoSuchFilmException();
        }
    }

    // curl http://localhost:8080/api/films/1?mediaType=json ==> returns JSON value
    // curl http://localhost:8080/api/films/1?mediaType=xml ==> returns XML value
    // see WebConfig for the configuration
    @GetMapping(value = "/{filmId}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public Film getFilmAsModel(@PathVariable final String filmId) {
        return new Film("Pulp Fiction", 1996);
    }

    // here we can send the request as JSON, thanks to @RequestBody
    // curl -X POST --data '{"number": "1"}' -H "Content-Type: application/json" http://localhost:8080/films/number/request
    @PostMapping(value = "/number/request", consumes = APPLICATION_JSON_UTF8_VALUE)
    public Film getFilmWithRequestObject(@RequestBody final FilmRequest filmRequest) {
        switch (filmRequest.getNumber()) {
            case 1: return new Film("Pulp Fiction", 1996);
            case 2: return new Film("Hateful 8", 2013);
            default: throw new RuntimeException("Unknown number");
        }
    }
}
