package danielh1307.springbootexample.film.infrastructure.support;

import danielh1307.springbootexample.film.domain.Film;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.lang.reflect.Field;

import static danielh1307.springbootexample.film.domain.FilmId.filmId;

public class FilmCreator {

    public static Film createFilm(String id, String title, int year) {
        Objenesis objenesis = new ObjenesisStd(); // standard, no constructor is called
        Film newFilm = objenesis.newInstance(Film.class);

        try {
            setValueToField(newFilm, "id", filmId(id));
            setValueToField(newFilm, "title", title);
            setValueToField(newFilm, "year", year);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return newFilm;
    }

    private static void setValueToField(Film film, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = Film.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(film, value);
    }
}
