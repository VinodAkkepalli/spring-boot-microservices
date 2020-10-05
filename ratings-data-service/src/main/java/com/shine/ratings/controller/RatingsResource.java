package com.shine.ratings.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shine.ratings.model.Rating;
import com.shine.ratings.model.UserRating;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping(value = "/movies/{movieId}", method = RequestMethod.GET)
    @ApiOperation(value="get rating of a given movie", 
    notes="Input movie name, output Rating object",
    response= Rating.class)
    public Rating getMovieRating( @ApiParam(value="name of the movie for returning rating", required=true)
    		@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
        UserRating userRating = new UserRating();
        userRating.initData(userId);
        return userRating;

    }

}
