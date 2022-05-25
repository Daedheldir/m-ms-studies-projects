package pl.edu.pwr.lab.main_project.data_generators;

import android.content.Context;
import android.media.Rating;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.Review;
import pl.edu.pwr.lab.main_project.accommodations.Accommodation;
import pl.edu.pwr.lab.main_project.events.Event;
import pl.edu.pwr.lab.main_project.guided_tours.Tour;
import pl.edu.pwr.lab.main_project.places.Place;
import pl.edu.pwr.lab.main_project.places.PlacesManager;

public class DataGenerator {
	private DataGenerator(){};

	public static Pair<Event,Place> createRandomEventPlacePair(Context context){
		String eventName = "Event \"" + LoremIpsumStringGenerator.getLoremRandomWord() + "\"";
		String eventDescription = eventName + " description. " + LoremIpsumStringGenerator.getLoremSubstring(10);

		String placeName = eventName + " place";
		String placeDescription = "Place where " + eventName + " takes place.";

		String[] places_img = context.getResources().getStringArray(R.array.events_img_webpages);
		ArrayList<String> leftImgs = new ArrayList<>(Arrays.asList(places_img));

		Random r = new Random();
		int photosAmount = 1 + r.nextInt(3);

		while (leftImgs.size() > photosAmount) {
			leftImgs.remove(r.nextInt(leftImgs.size()));
		}

		Place eventPlace = createSemiRandomPlace(context, placeName, placeDescription);
		Event event = new Event(
				eventName,
				eventDescription,
				eventPlace,
				leftImgs);

		return new Pair<>(event, eventPlace);
	}
	public static Place createSemiRandomPlace(Context context, String name, String description){
		Random r = new Random();
		Pair<Float, Float> placeCoords = new Pair<>(
				51.107883f + 0.08f - r.nextFloat() * 0.16f,
				17.038538f + 0.08f - r.nextFloat() * 0.16f);
		ArrayList<Review> placeReviews = new ArrayList<>();

		for(int i =0; i < 5; ++i){
			placeReviews.add(createRandomReview());
		}

		String[] places_img = context.getResources().getStringArray(R.array.places_img_webpages);
		ArrayList<String> leftPlacesImg = new ArrayList<>(Arrays.asList(places_img));

		int photosAmount = 1 + r.nextInt(5);

		while (leftPlacesImg.size() > photosAmount) {
			leftPlacesImg.remove(r.nextInt(leftPlacesImg.size()));
		}

		String[] video_ids = context.getResources().getStringArray(R.array.videos_youtube_ids);


		Place place = new Place(
				name,
				description,
				"",
				placeReviews,
				placeCoords,
				leftPlacesImg,
				video_ids[r.nextInt(video_ids.length)]
		);

		return place;
	}
	public static Place createRandomPlace(Context context){
		String placeName = "Place \"" + LoremIpsumStringGenerator.getLoremRandomWord() + "\"";
		String placeDescription = placeName+ " description:  " + LoremIpsumStringGenerator.getLoremSubstring(10);

		return createSemiRandomPlace(context, placeName, placeDescription);
	}
	public static Review createRandomReview(){
		Review review = new Review(
				Rating.newStarRating(Rating.RATING_5_STARS, new Random().nextFloat() * 5),
				"Review description: " + LoremIpsumStringGenerator.getLoremSubstring(10));
		return review;
	}
	public static Pair<Accommodation, Place> createRandomAccommodationPlacePair(Context context){
		String accomodationName = "Accommodation \"" + LoremIpsumStringGenerator.getLoremRandomWord() + "\"";
		String accommodationDescription = accomodationName+ " description:  " + LoremIpsumStringGenerator.getLoremSubstring(10);

		ArrayList<Review> reviews = new ArrayList<>();

		for(int i =0; i < 5; ++i){
			reviews.add(createRandomReview());
		}

		String placeName = accomodationName + " place";
		String placeDescription = "Place where " + accomodationName + " is.";
		Place place = createSemiRandomPlace(context, placeName, placeDescription);

		String[] accomodations_img = context.getResources().getStringArray(R.array.accommodations_img_webpages);
		ArrayList<String> leftImg = new ArrayList<>(Arrays.asList(accomodations_img));
		Random r = new Random();
		int photosAmount = 1 + r.nextInt(3);

		while (leftImg.size() > photosAmount) {
			leftImg.remove(r.nextInt(leftImg.size()));
		}
		Accommodation accommodation = new Accommodation(
				accomodationName,
				accommodationDescription,
				reviews,
				place,
				leftImg
		);

		return new Pair<>(accommodation, place);
	}
	public static Tour createRandomTour(){
		int placesCount = PlacesManager.getInstance().getCount();
		int visits = Math.min(placesCount, 5);

		ArrayList<Place> placesToVisit = new ArrayList<>();
		Random r = new Random();
		for(int i = 0; i < visits; ++i){
			placesToVisit.add(PlacesManager.getInstance().get(r.nextInt(placesCount)));
		}

		String tourName = "Tour \"" + LoremIpsumStringGenerator.getLoremRandomWord() + "\"";
		StringBuilder tourDescription = new StringBuilder("Tour which visits places: ");
		for(Place place : placesToVisit){
			tourDescription.append("\n- ").append(place.getName()).append(", ");
		}
		tourDescription.replace(tourDescription.lastIndexOf(","), tourDescription.length() - 1, "");

		Tour tour = new Tour(tourName, tourDescription.toString(), placesToVisit);
		return tour;
	}
}
