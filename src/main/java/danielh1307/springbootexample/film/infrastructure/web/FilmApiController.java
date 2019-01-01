package danielh1307.springbootexample.film.infrastructure.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        return this.filmService.getFilmTitle(filmId(filmId));
    }

    // curl http://localhost:8080/api/films/1?mediaType=json ==> returns JSON value
    // curl http://localhost:8080/api/films/1?mediaType=xml ==> returns XML value
    // curl http://localhost:8080/api/films/1?medaiType=csv ==> returns CSV value
    // see WebConfig for the configuration
    @GetMapping(value = "/films/{filmId}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "text/csv"})
    @ApiOperation("Returns the requested film either as JSON, XML or CSV (depending on accept header or parameter mediaType)")
    public Film getFilm(@PathVariable final String filmId) throws NoSuchFilmException {
        return this.filmService.getFilm(filmId(filmId));
    }

    @PostMapping(value = "/films", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Film addFilm(@RequestBody final AddNewFilmRequest filmRequest) {
        return this.filmService.addFilm(filmRequest.title, filmRequest.year);
    }

    // here we can send the request as JSON, thanks to @RequestBody
    // curl -X POST --data '{"filmKey": "1"}' -H "Content-Type: application/json" http://localhost:8080/films/number/request
    @PostMapping(value = "/films/generic-request", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Returns the requested film as JSON")
    public Film genericRequest(@RequestBody final FilmRequest filmRequest) throws NoSuchFilmException {
        return this.filmService.getFilm(filmId(filmRequest.getFilmKey()));
    }

    public static class AddNewFilmRequest {

        private String title;
        private int year;

        // Thanks to @JsonCreator we do not need a default constructor for Jackson
        @JsonCreator
        public AddNewFilmRequest(@JsonProperty("title") String title, @JsonProperty("year") int year) {
            this.title = title;
            this.year = year;
        }

        public String getTitle() {
            return this.title;
        }

        public int getYear() {
            return this.year;
        }
    }
}
