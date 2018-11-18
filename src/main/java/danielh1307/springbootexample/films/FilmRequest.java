package danielh1307.springbootexample.films;

public class FilmRequest {

    private int number;

    // this default constructor is needed, otherwise an object cannot be created from the request
    public FilmRequest() {}

    FilmRequest(int number) {
        this.number = number;
    }

    // TODO: check why this must be public
    public int getNumber() {
        return number;
    }
}
