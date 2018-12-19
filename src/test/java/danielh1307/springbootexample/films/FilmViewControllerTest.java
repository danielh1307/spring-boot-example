package danielh1307.springbootexample.films;

import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.htmlunit.LocalHostWebClient;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest // test against "external container"
//@WebMvcTest(controllers = FilmViewController.class)
public class FilmViewControllerTest {

    @Autowired
    private LocalHostWebClient localHostWebClient;

    /**
     * This is an example how HtmlUnit can be used in the tests.
     *
     * @throws Exception if an error occurs.
     */
    @Test
    public void getOverview_requestHtmlOverview_htmlOverviewReturned() throws Exception {
        // act
        HtmlPage htmlPage = localHostWebClient.getPage("http://localhost:8080/films/overview");
        HtmlElement bodyElement = htmlPage.getBody();
        HtmlElement filmName = getFirstHtmlElementsByItemprop(bodyElement, "filmtitle");
        HtmlElement filmYear = getFirstHtmlElementsByItemprop(bodyElement, "filmyear");

        // assert
        assertThat(filmName.getFirstChild().getNodeValue(), is(equalTo("Pulp Fiction")));
        assertThat(filmYear.getFirstChild().getNodeValue(), is(equalTo("1996")));
    }

    @Test
    public void uploadFilm_uploadFilmWithUploadForm_redirectedToCorrectPage() throws Exception {
        // arrange
        HtmlPage htmlPage = localHostWebClient.getPage("http://localhost:8080/films/addFilm");
        HtmlElement bodyElement = htmlPage.getBody();
        HtmlForm form = htmlPage.getForms().get(0);
        HtmlInput filmTitleInput = (HtmlInput) getFirstHtmlElementsByItemprop(bodyElement, "filmtitle");
        HtmlInput filmYearInput = (HtmlInput) getFirstHtmlElementsByItemprop(bodyElement, "filmyear");
        HtmlFileInput fileInput = (HtmlFileInput) getFirstHtmlElementsByItemprop(bodyElement, "filmcover");
        HtmlSubmitInput submitButton = form.getOneHtmlElementByAttribute("input", "type", "submit");
        filmTitleInput.setValueAttribute("Pulp Fiction");
        filmYearInput.setValueAttribute("1996");
        String fileUrl = getClass().getClassLoader().getResource("test.txt").toExternalForm();
        fileInput.setValueAttribute(fileUrl);

        // act
        HtmlPage redirectedPage = submitButton.click();

        // assert
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
