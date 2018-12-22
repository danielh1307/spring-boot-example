package danielh1307.springbootexample.film.infrastructure.web.support;

import danielh1307.springbootexample.film.domain.NoSuchFilmException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

// This class handles exceptions for all @Controller classes (e.g. NoSuchFilmException)
// The exception class itself is free from framework code
@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NoSuchFilmException.class)
    ResponseEntity<Object> handleNoSuchFilmException(NoSuchFilmException noSuchFilmException, WebRequest webRequest) {
        return handleExceptionInternal(noSuchFilmException, null, new HttpHeaders(), NOT_FOUND, webRequest);
    }
}
