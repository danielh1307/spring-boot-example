package danielh1307.springbootexample.films;

public class Film {

    private final String name;
    private final int year;

    public Film(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }
}
