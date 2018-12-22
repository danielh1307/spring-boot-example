package danielh1307.springbootexample.film.domain;

import danielh1307.springbootexample.film.domain.support.ValueWrapper;

public class FilmId extends ValueWrapper {

    private FilmId(String value) {
        super(value);
    }

    public static FilmId filmId(String value) {
        return new FilmId(value);
    }
}
