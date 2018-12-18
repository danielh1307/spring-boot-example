package danielh1307.springbootexample.films;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class FilmCsvHttpMessageConverter extends AbstractHttpMessageConverter<Film> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilmCsvHttpMessageConverter.class);

    public FilmCsvHttpMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Film.class.isAssignableFrom(clazz);
    }

    @Override
    protected Film readInternal(Class<? extends Film> clazz, HttpInputMessage inputMessage) {
        return null;
    }

    @Override
    protected void writeInternal(Film film, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        try (OutputStream outputStream = outputMessage.getBody()) {
            String outString = film.getTitle() + "," + film.getYear();
            outputStream.write(outString.getBytes());
        }
    }
}
