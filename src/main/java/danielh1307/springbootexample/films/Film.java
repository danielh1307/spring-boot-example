package danielh1307.springbootexample.films;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Here is a description for the film class")
public class Film {

    @ApiModelProperty(notes = "This is the title of the film")
    private final String title;
    @ApiModelProperty(notes = "This is the year when the film was released")
    private final int year;

    public Film(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    // TODO: check why this must be public
    public int getYear() {
        return year;
    }
}
