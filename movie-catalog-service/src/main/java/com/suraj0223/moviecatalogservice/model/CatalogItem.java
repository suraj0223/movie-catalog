package com.suraj0223.moviecatalogservice.model;

public class CatalogItem {
	private String description;
	private String name;
	private Integer rating;

	public CatalogItem(String name,String description, Integer rating) {
		super();
		this.description = description;
		this.name = name;
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
}
