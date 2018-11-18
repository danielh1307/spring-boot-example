package danielh1307.springbootexample.films;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/films")
public class FilmController {

    @GetMapping("/default")
    public String getFilmAsString() {
        return "Pulp Fiction";
    }

    @GetMapping("/number/{myNumber}")
    public String getFilmWithPathVariable(@PathVariable final int myNumber) {
        switch (myNumber) {
            case 1: return "Pulp Fiction";
            case 2: return "Hateful 8";
            default: return "Unknown";
        }
    }

    // we are using same mapping as getFilmAsString, but with a different content type
    // curl http://localhost:8080/films/default (or with -H "Accept: application/text") --> getFilmAsString --> String response
    // curl -H "accept: application/json" http://localhost:8080/films/default --> getFilmAsModel --> JSON response
    @GetMapping(value = "/default", produces = "application/json")
    public Film getFilmAsModel() {
        // this automatically returns the film in JSON format (thanks to @ResponseBody)
        return new Film("Pulp Fiction", 1996);
    }

    // here we can send the request as JSON, thanks to @RequestBody
    // curl -X POST --data '{"number": "1"}' -H "Content-Type: application/json" http://localhost:8080/films/number/request
    @PostMapping(value = "/number/request", consumes = "application/json")
    public Film getFilmWithRequestObject(@RequestBody final FilmRequest filmRequest) {
        switch (filmRequest.getNumber()) {
            case 1: return new Film("Pulp Fiction", 1996);
            case 2: return new Film("Hateful 8", 2013);
            default: throw new RuntimeException("Unknown number");
        }
    }

}
