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
	private List<Pair<Rating, String>> reviews;
	private String place;
	private List<Image> images;
}
