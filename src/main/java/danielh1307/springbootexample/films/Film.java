package danielh1307.springbootexample.films;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Here is a description for the film class")
public class Film {

    @ApiModelProperty(notes = "This is the name of the film")
    private final String name;
    @ApiModelProperty(notes = "This is the year when the film was released")
    private final int year;

    public Film(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    // TODO: check why this must be public
    public int getYear() {
        return year;
    }
}
