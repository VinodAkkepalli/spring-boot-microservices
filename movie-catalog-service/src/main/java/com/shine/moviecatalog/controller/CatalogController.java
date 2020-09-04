package com.shine.moviecatalog.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.shine.moviecatalog.model.CatalogItem;
import com.shine.moviecatalog.model.Movie;
import com.shine.moviecatalog.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

    	UserRating userRating = webClientBuilder.build()
    			.get()
    			.uri("http://ratings-data-service/ratingsdata/user/" + userId)
    			.retrieve()
    			.bodyToMono(UserRating.class)
    			.block();
    	
    	//Way to use RestTemplate for the same service call
//        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, UserRating.class);

        return userRating.getRatings().stream()
                .map(rating -> {
                	Movie movie = webClientBuilder.build().get()
                			.uri("http://movie-info-service/movies/" + rating.getMovieId())
                			.retrieve()
                			.bodyToMono(Movie.class)
                			.block();      
                	
                    return new CatalogItem(movie.getName(), movie.getName() + " Description", rating.getRating());
                })
                .collect(Collectors.toList());

    }
}