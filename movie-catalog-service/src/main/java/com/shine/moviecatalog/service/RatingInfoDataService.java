package com.shine.moviecatalog.service;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.shine.moviecatalog.model.Rating;
import com.shine.moviecatalog.model.UserRating;

@Service
public class RatingInfoDataService {

//  @Autowired
//  private RestTemplate restTemplate;
	
	@Autowired
	public Builder webClientBuilder;
	
	@HystrixCommand(fallbackMethod = "getFallBackUserRating",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="2000"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "50"),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "5000")
			})
	public UserRating getUserRating(String userId) {
		
		//Way to use RestTemplate for the same service call
//      UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, UserRating.class);
		
		return webClientBuilder.build()
    			.get()
    			.uri("http://ratings-data-service/ratingsdata/user/" + userId)
    			.retrieve()
    			.bodyToMono(UserRating.class)
    			.timeout(Duration.ofMillis(3000))	// set 3 seconds timeout at request level
    			.block();
	}
	
	public UserRating getFallBackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setRatings(Arrays.asList(new Rating("800",0)));
		return userRating;		
	}
}
