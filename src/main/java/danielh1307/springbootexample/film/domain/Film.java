package danielh1307.springbootexample.film.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Here is a description for the film class")
public class Film {

    @ApiModelProperty(notes = "This is the ID of the film")
    private final FilmId id;
    @ApiModelProperty(notes = "This is the title of the film")
    private final String title;
    @ApiModelProperty(notes = "This is the year when the film was released")
    private final int year;

    // We need @JsonCreator, otherwise Jackson cannot deserialize when no setters are used
    @JsonCreator
    private Film(@JsonProperty("id") FilmId id, @JsonProperty("title") String title, @JsonProperty("year") int year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public static Film newFilm(FilmId id, String title, int year) {
        return new Film(id, title, year);
    }

    public FilmId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    // TODO: check why this must be public
    public int getYear() {
        return year;
    }
}
