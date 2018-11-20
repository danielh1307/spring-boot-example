package danielh1307.springbootexample.films;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component("films.csv")
public class FilmsCsvView extends AbstractView {

    public FilmsCsvView() {
        super.setContentType("text/csv");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Film film = (Film) model.getOrDefault("films", null);
        response.setContentType(UTF_8.name());
        try (Writer out = new OutputStreamWriter(response.getOutputStream(), UTF_8)) {
            out.write(film.getName());
            out.write(",");
            out.write(String.valueOf(film.getYear()));
        }
        response.flushBuffer();
    }
}
