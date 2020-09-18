package com.shine.moviecatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.shine.moviecatalog.model.CatalogItem;
import com.shine.moviecatalog.model.Movie;
import com.shine.moviecatalog.model.Rating;

@Service
public class MovieInfoDataService {
	
	@Autowired
	public Builder webClientBuilder;

	public MovieInfoDataService() {
	}
	
	@HystrixCommand(fallbackMethod = "getFallBackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = webClientBuilder.build().get()
				.uri("http://movie-info-service/movies/" + rating.getMovieId())
				.retrieve()
				.bodyToMono(Movie.class)
				.block();      
		
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}
	
	public CatalogItem getFallBackCatalogItem(Rating rating) {
		return new CatalogItem("Movie name not found", "", rating.getRating());		
	}
	
}