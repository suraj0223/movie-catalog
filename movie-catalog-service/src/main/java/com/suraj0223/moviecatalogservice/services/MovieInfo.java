package com.suraj0223.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.suraj0223.moviecatalogservice.model.CatalogItem;
import com.suraj0223.moviecatalogservice.model.Movie;
import com.suraj0223.moviecatalogservice.model.Rating;

@Service
public class MovieInfo {
	@Autowired
	WebClient.Builder webClientBuilder;
	
	@HystrixCommand(fallbackMethod = "getCatalogItemFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10") })
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/api/movies/" + rating.getMovieId()).retrieve()
				.bodyToMono(Movie.class).block();
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}

	public CatalogItem getCatalogItemFallback(Rating rating) {
		return new CatalogItem("movie not found", "", rating.getRating());
	}
}
