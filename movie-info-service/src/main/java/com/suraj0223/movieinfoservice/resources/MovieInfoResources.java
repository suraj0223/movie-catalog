package com.suraj0223.movieinfoservice.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.suraj0223.movieinfoservice.model.Movie;
import com.suraj0223.movieinfoservice.model.MovieSummary;

@RestController
@RequestMapping("/api/movies")
public class MovieInfoResources {
	
    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;
    
	@GetMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
		String uriPath = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" +  apiKey;
        MovieSummary movieSummary = restTemplate.getForObject(uriPath, MovieSummary.class);
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }

}
