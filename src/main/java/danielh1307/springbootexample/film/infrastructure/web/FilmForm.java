package danielh1307.springbootexample.film.infrastructure.web;

import org.springframework.web.multipart.MultipartFile;

public class FilmForm {

    private String title;
    private int year;
    private MultipartFile cover;

    public MultipartFile getCover() {
        return cover;
    }

    public void setCover(MultipartFile cover) {
        this.cover = cover;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
