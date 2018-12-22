package danielh1307.springbootexample.film.boundary;

import danielh1307.springbootexample.film.domain.Film;
import danielh1307.springbootexample.film.domain.NoSuchFilmException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FilmService {

    private Map<String, Film> filmMap;

    public FilmService() {
        filmMap = new HashMap<>();
        filmMap.put("1", new Film("Pulp Fiction", 1994));
        filmMap.put("2", new Film("Hateful 8", 2014));
    }

    public String getFilmTitle(final String filmId) throws NoSuchFilmException {
        return film(filmId).getTitle();
    }

    public Film getFilm(final String filmId) throws NoSuchFilmException {
        return film(filmId);
    }

    private Film film(String filmId) throws NoSuchFilmException {
        return filmMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(filmId))
                .map(Map.Entry::getValue).findFirst()
                .orElseThrow(NoSuchFilmException::new);
    }
}
