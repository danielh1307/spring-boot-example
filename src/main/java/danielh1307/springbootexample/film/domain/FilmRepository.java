package danielh1307.springbootexample.film.domain;

public interface FilmRepository {

    Film getFilm(FilmId filmId) throws NoSuchFilmException;

    void addFilm(Film film);
}
