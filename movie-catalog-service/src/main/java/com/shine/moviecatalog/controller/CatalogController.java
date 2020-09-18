package com.shine.moviecatalog.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.shine.moviecatalog.model.CatalogItem;
import com.shine.moviecatalog.model.UserRating;
import com.shine.moviecatalog.service.MovieInfoDataService;
import com.shine.moviecatalog.service.RatingInfoDataService;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    WebClient.Builder webClientBuilder;
    
    @Autowired
    MovieInfoDataService movieInfoDataService;
    
    @Autowired
    RatingInfoDataService ratingInfoDataService;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

    	UserRating userRating = ratingInfoDataService.getUserRating(userId);
    	
    	return userRating.getRatings().stream()
                .map(rating -> movieInfoDataService.getCatalogItem(rating))
                .collect(Collectors.toList());
    }	
}