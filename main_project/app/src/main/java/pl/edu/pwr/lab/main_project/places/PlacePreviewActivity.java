package pl.edu.pwr.lab.main_project.places;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import pl.edu.pwr.lab.main_project.R;

public class PlacePreviewActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places_preview);

		TextView placeNameText = findViewById(R.id.places_preview_name_textView);
		TextView placeDescriptionText = findViewById(R.id.places_preview_description_textView);
		RatingBar placeRatingBar = findViewById(R.id.places_preview_ratingBar);

		if(!getIntent().hasExtra("placeIndex")) {
			return;
		}
			int index = getIntent().getIntExtra("placeIndex", 0);
			place = PlacesManager.getInstance().get(index);
		placeNameText.setText(place.getName());
		placeDescriptionText.setText(place.getDescription());
		placeRatingBar.setRating(place.getAverageRating());
	}

	private Place place;
}