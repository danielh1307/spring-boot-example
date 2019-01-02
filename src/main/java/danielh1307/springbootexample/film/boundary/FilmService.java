package danielh1307.springbootexample.film.boundary;

import danielh1307.springbootexample.film.domain.Film;
import danielh1307.springbootexample.film.domain.FilmId;
import danielh1307.springbootexample.film.domain.FilmRepository;
import danielh1307.springbootexample.film.domain.NoSuchFilmException;
import org.springframework.stereotype.Service;

import static danielh1307.springbootexample.film.domain.Film.newFilm;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public String getFilmTitle(final FilmId filmId) throws NoSuchFilmException {
        return film(filmId).getTitle();
    }

    public Film getFilm(final FilmId filmId) throws NoSuchFilmException {
        return film(filmId);
    }

    public Film addFilm(final String title, final int year) {
        Film film = newFilm(title, year);

        this.filmRepository.addFilm(film);

        return film;
    }

    private Film film(FilmId filmId) throws NoSuchFilmException {
        return this.filmRepository.getFilm(filmId);
    }
}
