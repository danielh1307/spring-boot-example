package danielh1307.springbootexample.film;

import danielh1307.springbootexample.film.boundary.FilmService;
import danielh1307.springbootexample.film.domain.Film;
import danielh1307.springbootexample.film.domain.FilmId;
import danielh1307.springbootexample.film.domain.FilmRepository;
import danielh1307.springbootexample.film.domain.NoSuchFilmException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static danielh1307.springbootexample.film.domain.Film.newFilm;
import static danielh1307.springbootexample.film.domain.FilmId.filmId;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FilmServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @MockBean
    private FilmRepository filmRepository;

    private FilmService filmService;

    @Before
    public void initialize() {
        filmService = new FilmService(filmRepository);
    }

    @Test
    public void getFilmTitle_withExistingFilm_correctTitleIsReturned() throws NoSuchFilmException {
        // arrange
        FilmId pulpFictionId = filmId("1");
        Film pulpFiction = newFilm(filmId("1"), "Pulp Fiction", 1994);
        when(filmRepository.getFilm(pulpFictionId)).thenReturn(pulpFiction);

        // act
        String filmTitle = filmService.getFilmTitle(pulpFictionId);

        // assert
        assertThat(filmTitle, is(equalTo("Pulp Fiction")));
    }

    @Test
    public void getFilm_withExistingFilm_correctFilmIsReturned() throws NoSuchFilmException {
        // arrange
        FilmId pulpFictionId = filmId("1");
        Film pulpFiction = newFilm(filmId("1"), "Pulp Fiction", 1994);
        when(filmRepository.getFilm(pulpFictionId)).thenReturn(pulpFiction);

        // act
        Film film = filmService.getFilm(pulpFictionId);

        // assert
        assertThat(film.getId(), is(equalTo(pulpFiction.getId())));
        assertThat(film.getTitle(), is(equalTo(pulpFiction.getTitle())));
        assertThat(film.getYear(), is(equalTo(pulpFiction.getYear())));
    }

    @Test
    public void getFilm_withMissingFilm_noSuchFilmExceptionIsThrown() throws NoSuchFilmException {
        // arrange
        when(filmRepository.getFilm(filmId("1"))).thenThrow(NoSuchFilmException.class);

        // act + assert
        expectedException.expect(NoSuchFilmException.class);
        filmService.getFilm(filmId("1"));
    }

}
