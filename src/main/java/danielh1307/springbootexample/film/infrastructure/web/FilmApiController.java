package danielh1307.springbootexample.film.infrastructure.web;

import danielh1307.springbootexample.film.boundary.FilmService;
import danielh1307.springbootexample.film.domain.Film;
import danielh1307.springbootexample.film.domain.NoSuchFilmException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import static danielh1307.springbootexample.film.domain.FilmId.filmId;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequestMapping("/api")
@Api(description = "This is the API controller for film model")
public class FilmApiController {

    private FilmService filmService;

    FilmApiController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films/{filmId}/title")
    @ApiOperation("Returns the title of a specific film as String")
    public String getFilmTitle(@PathVariable final String filmId) throws NoSuchFilmException {
        return filmService.getFilmTitle(filmId(filmId));
    }

    // curl http://localhost:8080/api/films/1?mediaType=json ==> returns JSON value
    // curl http://localhost:8080/api/films/1?mediaType=xml ==> returns XML value
    // curl http://localhost:8080/api/films/1?medaiType=csv ==> returns CSV value
    // see WebConfig for the configuration
    @GetMapping(value = "/films/{filmId}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "text/csv"})
    @ApiOperation("Returns the requested film either as JSON, XML or CSV (depending on accept header or parameter mediaType)")
    public Film getFilm(@PathVariable final String filmId) throws NoSuchFilmException {
        return filmService.getFilm(filmId(filmId));
    }

    // here we can send the request as JSON, thanks to @RequestBody
    // curl -X POST --data '{"number": "1"}' -H "Content-Type: application/json" http://localhost:8080/films/number/request
    @PostMapping(value = "/films/generic-request", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Returns the requested film as JSON")
    public Film genericRequest(@RequestBody final FilmRequest filmRequest) throws NoSuchFilmException {
        return filmService.getFilm(filmId(filmRequest.getFilmKey()));
    }


}
