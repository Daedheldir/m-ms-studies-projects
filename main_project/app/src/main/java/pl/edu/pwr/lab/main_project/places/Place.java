package pl.edu.pwr.lab.main_project.places;

import android.media.Rating;

import androidx.core.util.Pair;

import java.util.List;

import pl.edu.pwr.lab.main_project.Review;

//		>a. Name of place
//		>b. Description
//		c. Voice description (recording or read by assistant)
//		>d. Rate and rating functionality with review description
//		>e. Place location (with map/direction)
//		f. Images (up to 5)
//		g. Video (up to 30s)
public class Place {

	public Place(String name,
				 String description,
				 String voiceDescriptionFilepath,
				 List<Review> reviews,
				 Pair<Float, Float> locationCoords,
				 List<String> imageFilepaths,
				 String videoFilepath){
		setName(name);
		setDescription(description);
		setVoiceDescriptionFilepath(voiceDescriptionFilepath);
		setReviews(reviews);
		setLocationCoords(locationCoords);
		setImageFilepaths(imageFilepaths);
		setVideoFilepath(videoFilepath);
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

	public String getVoiceDescriptionFilepath() {
		return voiceDescriptionFilepath;
	}

	public void setVoiceDescriptionFilepath(String voiceDescriptionFilepath) {
		this.voiceDescriptionFilepath = voiceDescriptionFilepath;
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

	public Pair<Float, Float> getLocationCoords() {
		return locationCoords;
	}

	public void setLocationCoords(Pair<Float, Float> locationCoords) {
		this.locationCoords = locationCoords;
	}
	public void addImageFilepath(String imageFilepath){
		List<String> imgFilepaths = getImageUrls();
		imgFilepaths.add(imageFilepath);
		setImageFilepaths(imgFilepaths);
	}

	public List<String> getImageUrls() {
		return imageFilepaths;
	}

	private void setImageFilepaths(List<String> imageFilepaths){
		this.imageFilepaths = imageFilepaths;
	}

	public String getVideoFilepath() {
		return videoFilepath;
	}

	public void setVideoFilepath(String videoFilepath) {
		this.videoFilepath = videoFilepath;
	}

	private String name;
	private String description;
	private String voiceDescriptionFilepath;
	private List<Review> reviews;
	private Pair<Float, Float> locationCoords;
	private List<String> imageFilepaths;
	private String videoFilepath;
}
