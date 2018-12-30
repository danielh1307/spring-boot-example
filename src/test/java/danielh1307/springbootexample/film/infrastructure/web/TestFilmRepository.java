package danielh1307.springbootexample.film.infrastructure.web;

import danielh1307.springbootexample.film.domain.Film;
import danielh1307.springbootexample.film.domain.FilmId;
import danielh1307.springbootexample.film.domain.FilmRepository;
import danielh1307.springbootexample.film.domain.NoSuchFilmException;
import org.springframework.boot.test.context.TestConfiguration;

import static danielh1307.springbootexample.film.domain.Film.newFilm;
import static danielh1307.springbootexample.film.domain.FilmId.filmId;

// TestConfiguration can be used to define additional beans just for testing
@TestConfiguration
public class TestFilmRepository implements FilmRepository {

        private final Film PULP_FICTION;
        private final Film HATEFUL_8;

        public TestFilmRepository() {
            PULP_FICTION = newFilm(filmId("1"), "Pulp Fiction", 1994);
            HATEFUL_8 = newFilm(filmId("2"), "Hateful 8", 2014);
        }

        @Override
        public Film getFilm(FilmId filmId) throws NoSuchFilmException {
            if (filmId.getValue().equals("1")) {
                return PULP_FICTION;
            }
            if (filmId.getValue().equals("2")) {
                return HATEFUL_8;
            }
            throw new NoSuchFilmException();
        }

        @Override
        public void addFilm(Film film) {}
}
