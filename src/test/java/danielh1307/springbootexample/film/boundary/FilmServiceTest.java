package danielh1307.springbootexample.film.boundary;

import danielh1307.springbootexample.film.domain.Film;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FilmServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @MockBean
    private FilmRepository filmRepository;

    private FilmService filmService;

    @Before
    public void initialize() {
        this.filmService = new FilmService(this.filmRepository);
    }

    @Test
    public void getFilmTitle_withExistingFilm_correctTitleIsReturned() throws NoSuchFilmException {
        // arrange
        Film pulpFiction = newFilm("Pulp Fiction", 1994);
        when(this.filmRepository.getFilm(pulpFiction.getId())).thenReturn(pulpFiction);

        // act
        String filmTitle = this.filmService.getFilmTitle(pulpFiction.getId());

        // assert
        assertThat(filmTitle, is(equalTo("Pulp Fiction")));
    }

    @Test
    public void getFilm_withExistingFilm_correctFilmIsReturned() throws NoSuchFilmException {
        // arrange
        Film pulpFiction = newFilm("Pulp Fiction", 1994);
        when(this.filmRepository.getFilm(pulpFiction.getId())).thenReturn(pulpFiction);

        // act
        Film film = this.filmService.getFilm(pulpFiction.getId());

        // assert
        assertThat(film.getId(), is(equalTo(pulpFiction.getId())));
        assertThat(film.getTitle(), is(equalTo(pulpFiction.getTitle())));
        assertThat(film.getYear(), is(equalTo(pulpFiction.getYear())));
    }

    @Test
    public void getFilm_withMissingFilm_noSuchFilmExceptionIsThrown() throws NoSuchFilmException {
        // arrange
        when(this.filmRepository.getFilm(filmId("1"))).thenThrow(NoSuchFilmException.class);

        // act + assert
        this.expectedException.expect(NoSuchFilmException.class);
        this.filmService.getFilm(filmId("1"));
    }

    @Test
    public void addFilm_addNewFilm_testFilmWasAdded() {
        // act
        this.filmService.addFilm("Reservoir Dogs", 1992);

        // assert
        verify(this.filmRepository, times(1)).addFilm(any());
    }

}
