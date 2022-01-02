package com.suraj0223.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.suraj0223.moviecatalogservice.model.CatalogItem;
import com.suraj0223.moviecatalogservice.model.UserRating;
import com.suraj0223.moviecatalogservice.services.MovieInfo;
import com.suraj0223.moviecatalogservice.services.RatingInfo;

@RestController
@RequestMapping("/api/catalog")
public class MovieCatalogResource {

	@Autowired
	MovieInfo movieInfo;

	@Autowired
	RatingInfo ratingInfo;

	@GetMapping("/{userId}")
	@HystrixCommand(fallbackMethod = "getCatalogFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10") })
	public List<CatalogItem> getAllCatalog(@PathVariable String userId) {

		// get all rated movies Id's
		UserRating userRatings = ratingInfo.getRatingInfoDetail(userId);

		// for each movie id call movie info and get details
		return userRatings.getRatings().stream().map(rating -> movieInfo.getCatalogItem(rating))
				.collect(Collectors.toList());
	}

	public List<CatalogItem> getCatalogFallback(@PathVariable String userId) {
		List<CatalogItem> catalogItems = Arrays.asList(new CatalogItem("name not found!!", "", 0));
		return catalogItems;
	}

}
