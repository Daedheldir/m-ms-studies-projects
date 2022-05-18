package pl.edu.pwr.lab.main_project;

import android.media.Image;
import android.media.Rating;
import android.provider.MediaStore;

import androidx.core.util.Pair;

import java.util.List;

//		a. Name of place
//		b. Description
//		c. Voice description (recording or read by assistant)
//		d. Rate and rating functionality with review description
//		e. Place location (with map/direction)
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
		List<String> imgFilepaths = getImageFilepaths();
		imgFilepaths.add(imageFilepath);
		setImageFilepaths(imgFilepaths);
	}

	public List<String> getImageFilepaths() {
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
