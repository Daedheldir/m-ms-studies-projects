package pl.edu.pwr.lab.main_project;

import android.media.Rating;

public class Review {
	public Review(Rating rating, String description){
		setRating(rating);
		setDescription(description);
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Rating rating;
	private String description;
}
