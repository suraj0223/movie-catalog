package com.suraj0223.moviecatalogservice.model;

import java.util.List;

public class UserRating {
	
	private String userId;
	private List<Rating> ratings;

	public UserRating() {

	}
	
	public UserRating(String userId, List<Rating> ratings) {
		this.setUserId(userId);
		this.ratings = ratings;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}