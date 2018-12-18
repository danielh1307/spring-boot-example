package danielh1307.springbootexample.films;

import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.htmlunit.LocalHostWebClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocalHostWebClient localHostWebClient;

    /**
     * This is an example how HtmlUnit can be used in the tests.
     *
     * @throws Exception if an error occurs.
     */
    @Test
    public void htmlFilmOverviewShouldWork() throws Exception {
        HtmlPage htmlPage = localHostWebClient.getPage("http://localhost:8080/films/overview");
        HtmlElement bodyElement = htmlPage.getBody();

        HtmlElement filmName = getFirstHtmlElementsByItemprop(bodyElement, "filmtitle");
        HtmlElement filmYear = getFirstHtmlElementsByItemprop(bodyElement, "filmyear");
        assertThat(filmName.getFirstChild().getNodeValue(), is(equalTo("Pulp Fiction")));
        assertThat(filmYear.getFirstChild().getNodeValue(), is(equalTo("1996")));
    }

    @Test
    public void htmlFilmUploadFormIsCorrect() throws Exception {
        HtmlPage htmlPage = localHostWebClient.getPage("http://localhost:8080/films/addFilm");

        HtmlElement bodyElement = htmlPage.getBody();

        HtmlForm form = htmlPage.getForms().get(0);
        HtmlInput filmTitleInput = (HtmlInput) getFirstHtmlElementsByItemprop(bodyElement, "filmtitle");
        HtmlInput filmYearInput = (HtmlInput) getFirstHtmlElementsByItemprop(bodyElement, "filmyear");
//        HtmlFileInput fileInput = (HtmlFileInput) getFirstHtmlElementsByItemprop(bodyElement, "filmcover");
        HtmlSubmitInput submitButton = form.getOneHtmlElementByAttribute("input", "type", "submit");

        filmTitleInput.setValueAttribute("Pulp Fiction");
        filmYearInput.setValueAttribute("1996");
//        fileInput.setValueAttribute("blahblah");
//        fileInput.setData("abcdef".getBytes());
//        fileInput.setContentType("application/pdf");
        HtmlPage redirectedPage = submitButton.click();

        bodyElement = redirectedPage.getBody();

        HtmlElement fileNameElement = getFirstHtmlElementsByItemprop(bodyElement, "filename");
        assertThat(fileNameElement.getFirstChild().getNodeValue(), is(equalTo("Pulp Fiction-1996.jpg")));
    }

    private HtmlElement getFirstHtmlElementsByItemprop(HtmlElement htmlElement, String itemprop) {
        return stream(htmlElement.getHtmlElementDescendants().spliterator(), false)
                .filter((element) -> element.getAttribute("itemprop").equals(itemprop))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("HTML element with itemprop " + itemprop + " not found"));
    }
}
