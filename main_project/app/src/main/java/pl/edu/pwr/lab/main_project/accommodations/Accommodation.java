package pl.edu.pwr.lab.main_project.accommodations;

import android.media.Rating;

import java.util.List;

import pl.edu.pwr.lab.main_project.Review;
import pl.edu.pwr.lab.main_project.places.Place;

//	5. Accommodation module should include:
//		>a. Name
//		>b. Description
//		>c. Rate and rating functionality with review description
//		>d. Place location (with map/direction)
//		>e. Images. (up to 3)
public class Accommodation {

	public Accommodation(String name, String description, List<Review> reviews, Place place, List<String> images){
		this.name = name;
		this.description = description;
		this.reviews = reviews;
		this.place = place;
		this.images = images;
	}

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
	public float getAverageRating(){
		float avgRating = 0;
		for(Review review : getReviews()){
			avgRating += review.getRating().getStarRating();
		}
		avgRating /= getReviews().size();
		return avgRating;
	}
	private void setReviews(List<Review> reviews){
		this.reviews = reviews;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public List<String> getImageUrls() {
		return images;
	}

	private void setImagesUrls(List<String> images){
		this.images = images;
	}

	private String name;
	private String description;
	private List<Review> reviews;
	private Place place;
	private List<String> images;
}
