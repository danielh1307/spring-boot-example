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
        LOGGER.info("I am created");
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Film.class.isAssignableFrom(clazz);
    }

    @Override
    protected Film readInternal(Class<? extends Film> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        LOGGER.info("readInternal called");
        return null;
    }

    @Override
    protected void writeInternal(Film film, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        LOGGER.info("writeInternal called");
        try (OutputStream outputStream = outputMessage.getBody()) {
            outputStream.write("Hello".getBytes());
        }
    }
}
