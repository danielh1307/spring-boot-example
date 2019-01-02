package danielh1307.springbootexample.film.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import danielh1307.springbootexample.film.domain.support.ValueWrapper;

import static java.util.UUID.randomUUID;

public class FilmId extends ValueWrapper {

    // We need @JsonCreator, otherwise Jackson cannot deserialize when no setters are used
    @JsonCreator
    private FilmId(@JsonProperty("value") String value) {
        super(value);
    }

    public static FilmId filmId(String value) {
        return new FilmId(value);
    }

    static FilmId newFilmId() {
        return new FilmId(randomUUID().toString());
    }
}
