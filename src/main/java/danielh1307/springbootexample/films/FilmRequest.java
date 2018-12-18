package danielh1307.springbootexample.films;

public class FilmRequest {

    private String filmKey;

    // this default constructor is needed, otherwise an object cannot be created from the request
    public FilmRequest() {}

    FilmRequest(String filmKey) {
        this.filmKey = filmKey;
    }

    // TODO: check why this must be public
    // check this: https://stackoverflow.com/questions/28466207/could-not-find-acceptable-representation-using-spring-boot-starter-web
    public String getFilmKey() {
        return filmKey;
    }
}
