package danielh1307.springbootexample.film.infrastructure.support;

import danielh1307.springbootexample.film.domain.Film;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static danielh1307.springbootexample.film.infrastructure.support.FilmCreator.createFilm;
import static java.util.Arrays.stream;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FilmCreatorTest {

    @Test
    public void createFilm_withAllAttributes_attributesAreSetCorrectly() {
        Film newFilm = createFilm("1", "Titel", 2000);

        assertThat(newFilm.getId().getValue(), is(equalTo("1")));
        assertThat(newFilm.getTitle(), is(equalTo("Titel")));
        assertThat(newFilm.getYear(), is(equalTo(2000)));
    }

    @Test
    public void filmCreator_createsAllAttributes() {
        Field[] allFieldsOfFilm = Film.class.getDeclaredFields();

        Method createFilmMethod = stream(FilmCreator.class.getMethods())
                .filter((m) -> m.getName().equals("createFilm"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Method createFilm not found"));
        assertThat(createFilmMethod.getParameterCount(), is(equalTo(allFieldsOfFilm.length)));
    }
}
