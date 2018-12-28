package danielh1307.springbootexample.film;

import danielh1307.springbootexample.film.domain.Film;
import danielh1307.springbootexample.film.domain.FilmId;
import danielh1307.springbootexample.film.domain.FilmRepository;
import danielh1307.springbootexample.film.domain.NoSuchFilmException;
import org.springframework.stereotype.Repository;

import static danielh1307.springbootexample.film.domain.Film.newFilm;
import static danielh1307.springbootexample.film.domain.FilmId.filmId;

@Repository
public class TestFilmRepository implements FilmRepository {

    @Override
    public Film getFilm(FilmId filmId) throws NoSuchFilmException {
        if (filmId.getValue().equals("1")) {
            return newFilm(filmId("1"), "Pulp Fiction", 1994);
        }
        if (filmId.getValue().equals("2")) {
            return newFilm(filmId("2"), "Hateful 8", 2014);
        }
        throw new NoSuchFilmException();
    }

    @Override
    public void addFilm(Film film) {

    }
}
