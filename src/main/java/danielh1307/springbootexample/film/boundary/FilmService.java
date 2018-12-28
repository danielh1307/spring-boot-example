package danielh1307.springbootexample.film.boundary;

import danielh1307.springbootexample.film.domain.Film;
import danielh1307.springbootexample.film.domain.FilmId;
import danielh1307.springbootexample.film.domain.FilmRepository;
import danielh1307.springbootexample.film.domain.NoSuchFilmException;
import org.springframework.stereotype.Service;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public String getFilmTitle(final FilmId filmId) throws NoSuchFilmException {
        return film(filmId).getTitle();
    }

    public Film getFilm(final FilmId filmId) throws NoSuchFilmException {
        return film(filmId);
    }

    private Film film(FilmId filmId) throws NoSuchFilmException {
        return filmRepository.getFilm(filmId);
    }
}
