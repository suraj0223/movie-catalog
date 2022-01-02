package com.suraj0223.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.suraj0223.moviecatalogservice.model.Rating;
import com.suraj0223.moviecatalogservice.model.UserRating;

@Service
public class RatingInfo {
	@Autowired
	WebClient.Builder webClientBuilder;

	@HystrixCommand(fallbackMethod = "ratingFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10") })
	public UserRating getRatingInfoDetail(String userId) {
		return webClientBuilder.build().get().uri("http://localhost:8083/api/ratingsdata/users/" + userId).retrieve()
				.bodyToMono(UserRating.class).block();
//		UserRating userRatings = restTemplate.getForObject("http://localhost:8083/api/ratingsdata/users/" + userId, UserRating.class);
//		return userRatings;
	}

	public UserRating ratingFallback(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setRatings(Arrays.asList(new Rating("null", 0)));
		return userRating;
	}
}
