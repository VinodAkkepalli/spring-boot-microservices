package com.shine.ratings.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Details about rating of a movie")
public class Rating {

	@ApiModelProperty(notes = "unique Movie name")
    private String movieId;
	@ApiModelProperty(notes = "rating of the movie")
    private int rating;
}
