package pl.edu.pwr.lab.main_project.data_generators;

import android.media.Rating;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Random;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.Review;
import pl.edu.pwr.lab.main_project.accommodations.Accommodation;
import pl.edu.pwr.lab.main_project.events.Event;
import pl.edu.pwr.lab.main_project.places.Place;
import pl.edu.pwr.lab.main_project.places.PlacesManager;

public class DataGenerator {
	private DataGenerator(){};

	public static Pair<Event,Place> createRandomEventPlacePair(){
		String eventName = "Event \"" + LoremIpsumStringGenerator.getLoremRandomWord() + "\"";
		String eventDescription = eventName + " description. " + LoremIpsumStringGenerator.getLoremSubstring(10);

		String placeName = eventName + " place";
		String placeDescription = "Place where " + eventName + " takes place.";


		Place eventPlace = createSemiRandomPlace(placeName, placeDescription);
		Event event = new Event(
				eventName,
				eventDescription,
				eventPlace,
				"");

		return new Pair<>(event, eventPlace);
	}
	public static Place createSemiRandomPlace(String name, String description){
		Random r = new Random();
		Pair<Float, Float> placeCoords = new Pair<>(r.nextFloat() * 50, r.nextFloat() * 50);
		ArrayList<Review> placeReviews = new ArrayList<>();

		for(int i =0; i < 5; ++i){
			placeReviews.add(createRandomReview());
		}

		Place place = new Place(
				name,
				description,
				"",
				placeReviews,
				placeCoords,
				new ArrayList<>(),
				""
		);

		return place;
	}
	public static Place createRandomPlace(){
		String placeName = "Place \"" + LoremIpsumStringGenerator.getLoremRandomWord() + "\"";
		String placeDescription = placeName+ " description:  " + LoremIpsumStringGenerator.getLoremSubstring(10);

		return createSemiRandomPlace(placeName, placeDescription);
	}

	public static Review createRandomReview(){
		Review review = new Review(
				Rating.newStarRating(Rating.RATING_5_STARS, new Random().nextFloat() * 5),
				"Review description: " + LoremIpsumStringGenerator.getLoremSubstring(10));
		return review;
	}

	public static Pair<Accommodation, Place> createRandomAccomodationPlacePair(){
		String accomodationName = "Accomodation \"" + LoremIpsumStringGenerator.getLoremRandomWord() + "\"";
		String accommodationDescription = accomodationName+ " description:  " + LoremIpsumStringGenerator.getLoremSubstring(10);

		ArrayList<Review> reviews = new ArrayList<>();

		for(int i =0; i < 5; ++i){
			reviews.add(createRandomReview());
		}

		String placeName = accomodationName + " place";
		String placeDescription = "Place where " + accomodationName + " is.";
		Place place = createSemiRandomPlace(placeName, placeDescription);

		Accommodation accommodation = new Accommodation(
				accomodationName,
				accommodationDescription,
				reviews,
				place,
				new ArrayList<>()
		);

		return new Pair<>(accommodation, place);
	}
}