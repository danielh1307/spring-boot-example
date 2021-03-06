package danielh1307.springbootexample.film.infrastructure.web;

import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.htmlunit.LocalHostWebClient;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
// we are using SpringBootTest together with LocalHostWebClient - so this is some kind of "integration test"
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class FilmViewControllerTest {

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
    public void getOverview_requestHtmlOverview_htmlOverviewReturned() throws Exception {
        // act
        HtmlPage htmlPage = this.localHostWebClient.getPage("/films/overview");
        HtmlElement bodyElement = htmlPage.getBody();
        HtmlElement filmName = getFirstHtmlElementsByItemprop(bodyElement, "filmtitle");
        HtmlElement filmYear = getFirstHtmlElementsByItemprop(bodyElement, "filmyear");

        // assert
        assertThat(filmName.getFirstChild().getNodeValue(), is(equalTo("Pulp Fiction")));
        assertThat(filmYear.getFirstChild().getNodeValue(), is(equalTo("1996")));
    }

    @Test
    public void uploadFilm_formUpload_redirectsToCorrectPage() throws Exception {
        // arrange
        HtmlPage htmlPage = this.localHostWebClient.getPage("/films/addFilm");
        HtmlElement bodyElement = htmlPage.getBody();
        HtmlForm form = htmlPage.getForms().get(0);
        HtmlInput filmTitleInput = (HtmlInput) getFirstHtmlElementsByItemprop(bodyElement, "filmtitle");
        HtmlInput filmYearInput = (HtmlInput) getFirstHtmlElementsByItemprop(bodyElement, "filmyear");
        HtmlFileInput fileInput = (HtmlFileInput) getFirstHtmlElementsByItemprop(bodyElement, "filmcover");
        HtmlSubmitInput submitButton = form.getOneHtmlElementByAttribute("input", "type", "submit");
        filmTitleInput.setValueAttribute("Pulp Fiction");
        filmYearInput.setValueAttribute("1996");
        String fileUrl = getClass().getClassLoader().getResource("test.txt").toExternalForm();
        fileInput.setFiles(new File(fileUrl));

        // act
        HtmlPage redirectedPage = submitButton.click();

        // assert
        bodyElement = redirectedPage.getBody();
        HtmlElement fileNameElement = getFirstHtmlElementsByItemprop(bodyElement, "filename");
        assertThat(fileNameElement.getFirstChild().getNodeValue(), is(equalTo("Pulp Fiction-1996.jpg")));
    }

    @Test
    public void uploadFilm_postCall_returnsCorrectRedirectedUrl() throws Exception {
        this.mockMvc.
                perform(multipart("/films/filmUpload")
                        .file("cover", "123".getBytes())
                        .param("title", "Pulp Fiction")
                        .param("year", "1996")
                        .with(SecurityMockMvcRequestPostProcessors.user("some_user"))
                )
                .andExpect(redirectedUrl("filmUploaded?filename=Pulp+Fiction-1996.jpg"));
    }

    private HtmlElement getFirstHtmlElementsByItemprop(HtmlElement htmlElement, String itemprop) {
        return stream(htmlElement.getHtmlElementDescendants().spliterator(), false)
                .filter((element) -> element.getAttribute("itemprop").equals(itemprop))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("HTML element with itemprop " + itemprop + " not found"));
    }

}
