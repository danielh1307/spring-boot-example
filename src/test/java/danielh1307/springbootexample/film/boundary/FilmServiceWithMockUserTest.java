package danielh1307.springbootexample.film.boundary;

import danielh1307.springbootexample.film.domain.Film;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(
        username = "anything_possible_here",
        roles = {"ADMIN"}
)
public class FilmServiceWithMockUserTest {

    @Autowired
    private FilmService filmService;

    @Rule
    public ExpectedException expectedException = none();

    @Test
    public void getSecretFilm_withMockUserWithRoleAdmin_secretFilmIsReturned() {
        // act
        Film secretFilm = this.filmService.getSecretFilm();

        // assert
        assertThat(secretFilm, is(notNullValue()));
    }

    @Test
    @WithMockUser(username = "whatever", roles = {"NO_ADMIN"})
    public void getSecretFilm_withMockUserWithoutRoleAdmin_accessDenied() {
        // assert
        this.expectedException.expect(AccessDeniedException.class);

        // act
        this.filmService.getSecretFilm();
    }
}
