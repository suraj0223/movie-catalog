package com.suraj0223.moviedataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraj0223.moviedataservice.model.Rating;
import com.suraj0223.moviedataservice.model.UserRating;

@RestController
@RequestMapping("/api/ratingsdata")
public class RatingResource {

	@GetMapping("/movies/{movieId}")
	public Rating getMovieRating(@PathVariable String movieId) {
		return new Rating(movieId, 4);
	}

	@GetMapping("/users/{userId}")
	public UserRating getUserRatings(@PathVariable String userId) {

		List<Rating> ratingList = Arrays.asList(new Rating("550", 4), new Rating("551", 3));
		UserRating userRatings = new UserRating();
		userRatings.setUserId(userId);
		userRatings.setRatings(ratingList);

		return userRatings;
	}
}