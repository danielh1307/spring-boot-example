package danielh1307.springbootexample.films;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// TODO: check why we need those settings here
@WebMvcTest(
        controllers = FilmController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {FilmsCsvView.class})
)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * This is an example how HtmlUnit can be used in the tests.
     *
     * @throws Exception if an error occurs.
     */
    @Test
    public void htmlFilmOverviewShouldWork() throws Exception {
        HtmlPage htmlPage;
        try (final WebClient webClient = webAppContextSetup(webApplicationContext).build()) {
            htmlPage = webClient.getPage("http://localhost:8080/films/overview");
        }
        HtmlElement bodyElement = htmlPage.getBody();

        List<HtmlElement> htmlElementsFilmName = getHtmlElementsByItemprop(bodyElement, "filmname");
        List<HtmlElement> htmlElementsFilmYear = getHtmlElementsByItemprop(bodyElement, "filmyear");
        assertThat(htmlElementsFilmName, hasSize(1));
        assertThat(htmlElementsFilmYear, hasSize(1));
        assertThat(htmlElementsFilmName.get(0).getFirstChild().getNodeValue(), is(equalTo("Pulp Fiction")));
        assertThat(htmlElementsFilmYear.get(0).getFirstChild().getNodeValue(), is(equalTo("1996")));
    }

    // TODO: This test currently fails since the attributes for content negotiation are commented in application.properties.
    // I am changing the ContentNegotiationConfigurer in WebConfig, and currently I do not know how to adjust it
    // to have the same effect as the properties in application.properties.
    @Test
    @Ignore
    public void csvViewShouldWork() throws Exception {
        mockMvc
                .perform(get("/films/overview").accept("text/csv"))
                .andExpect(status().isOk())
                .andExpect(view().name("films"))
                .andExpect(content().string("Pulp Fiction,1996"));
    }

    private List<HtmlElement> getHtmlElementsByItemprop(HtmlElement htmlElement, String itemprop) {
        return stream(htmlElement.getHtmlElementDescendants().spliterator(), false)
                .filter((element) -> element.getAttribute("itemprop").equals(itemprop))
                .collect(toList());
    }
}
