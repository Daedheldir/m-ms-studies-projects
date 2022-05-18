package pl.edu.pwr.lab.main_project;

import android.media.Image;
import android.media.Rating;

import androidx.core.util.Pair;

import java.util.List;

//	5. Accommodation module should include:
//		a. Name
//		b. Description
//		c. Rate and rating functionality with review description
//		d. Place location (with map/direction)
//		e. Images. (up to 3)
public class Accomodation {

	private String name;
	private String description;
	private List<Review> reviews;
	private String place;
	private List<Image> images;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addReview(Rating rating, String review){
		List<Review> ratingsList = getReviews();
		ratingsList.add(new Review(rating, review));
		setReviews(ratingsList);
	}
	public List<Review> getReviews() {
		return reviews;
	}

	private void setReviews(List<Review> reviews){
		this.reviews = reviews;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public List<Image> getImages() {
		return images;
	}

	private void setImages(List<Image> images){
		this.images = images;
	}
}
