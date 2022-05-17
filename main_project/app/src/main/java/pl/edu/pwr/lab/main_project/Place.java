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

	private String name;
	private String description;
	private MediaStore.Audio voiceDescription;
	private List<Pair<Rating, String>> reviews;
	private Pair<Float, Float> locationCoords;
	private List<Image> images;
	private MediaStore.Video video;
}
