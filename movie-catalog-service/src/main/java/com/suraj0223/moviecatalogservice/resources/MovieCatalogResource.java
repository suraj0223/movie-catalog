package com.suraj0223.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.suraj0223.moviecatalogservice.model.CatalogItem;
import com.suraj0223.moviecatalogservice.model.Movie;
import com.suraj0223.moviecatalogservice.model.Rating;
import com.suraj0223.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/api/catalog")
public class MovieCatalogResource {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired 
	WebClient.Builder webClientBuilder;

	@GetMapping("/{userId}")
	public List<CatalogItem> getAllCatalog(@PathVariable String userId) {

		// get all rated movies Id's
		UserRating userRatings = getRatingInfoDetail(userId);

		// for each movie id call movie info and get details
		return userRatings.getRatings().stream().map(rating -> {
//			Movie movie = restTemplate.getForObject("http://localhost:8082/api/movies/" + rating.getMovieId(),
//					Movie.class);
			Movie movie = getMovieInfo(rating);
					
			return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
		}).collect(Collectors.toList());

	}

	private UserRating getRatingInfoDetail(String userId) {
		return webClientBuilder
				.build()
				.get()
				.uri("http://localhost:8083/api/ratingsdata/users/" + userId)
				.retrieve()
				.bodyToMono(UserRating.class)
				.block();
//		UserRating userRatings = restTemplate.getForObject("http://localhost:8083/api/ratingsdata/users/" + userId, UserRating.class);
//		return userRatings;
	}

	private Movie getMovieInfo(Rating rating) {
		return webClientBuilder
				.build()
				.get()
				.uri("http://localhost:8082/api/movies/" + rating.getMovieId())
				.retrieve()
				.bodyToMono(Movie.class)
				.block();
	}
}
