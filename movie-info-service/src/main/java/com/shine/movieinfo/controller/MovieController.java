package com.shine.movieinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.shine.movieinfo.model.Movie;
import com.shine.movieinfo.model.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
    WebClient.Builder webClientBuilder;

	@Value("${api.key}")
	private String apiKey;
	
    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
    	MovieSummary movieSummary = webClientBuilder.build().get()
		.uri("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" +  apiKey)
		.retrieve()
		.bodyToMono(MovieSummary.class)
		.block();
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }

}
