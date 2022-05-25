package pl.edu.pwr.lab.main_project.places;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.ReviewsRecyclerViewAdapter;

public class PlacePreviewActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places_preview);

		TextView placeNameText = findViewById(R.id.places_preview_name_textView);
		TextView placeDescriptionText = findViewById(R.id.places_preview_description_textView);

		if(!getIntent().hasExtra("placeIndex")) {
			return;
		}
		int index = getIntent().getIntExtra("placeIndex", 0);
		place = PlacesManager.getInstance().get(index);
		placeNameText.setText(place.getName());
		placeDescriptionText.setText(place.getDescription());

		RecyclerView recyclerView = findViewById(R.id.places_preview_recyclerView);
		recyclerView.setAdapter(new ReviewsRecyclerViewAdapter(place.getReviews()));
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		WebView webView;
		webView = findViewById(R.id.place_preview_webView);

		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);

		float coordX = place.getLocationCoords().first;
		float coordY = place.getLocationCoords().second;

		String apikey = getString(R.string.maps_api_key);

		webView.loadUrl("https://maps.googleapis.com/maps/api/" +
				"staticmap?center=" + coordX +"," + coordY +
				"&zoom=10" +
				"&size=512x512" +
				"&markers=color:blue%7C" + + coordX +"," + coordY +
				"&key="+apikey);
	}

	private Place place;
}