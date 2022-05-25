package pl.edu.pwr.lab.main_project.accommodations;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.ReviewsRecyclerViewAdapter;

public class AccommodationPreviewActivity extends AppCompatActivity {
	private Accommodation accommodation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accommodation_preview);

		TextView placeNameText = findViewById(R.id.accommodations_preview_name_textView);
		TextView placeDescriptionText = findViewById(R.id.accommodations_preview_description_textView);

		if(!getIntent().hasExtra("accommodationIndex")) {
			return;
		}
		int index = getIntent().getIntExtra("accommodationIndex", 0);
		accommodation = AccommodationsManager.getInstance().get(index);
		placeNameText.setText(accommodation.getName());
		placeDescriptionText.setText(accommodation.getDescription());

		RecyclerView recyclerView = findViewById(R.id.accommodations_preview_recyclerView);
		recyclerView.setAdapter(new ReviewsRecyclerViewAdapter(accommodation.getReviews()));
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		WebView webView;
		webView = findViewById(R.id.accommodations_preview_webView);

		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);

		float coordX = accommodation.getPlace().getLocationCoords().first;
		float coordY = accommodation.getPlace().getLocationCoords().second;

		String apikey = getString(R.string.maps_api_key);

		webView.loadUrl("https://maps.googleapis.com/maps/api/" +
				"staticmap?center=" + coordX +"," + coordY +
				"&zoom=10" +
				"&size=512x512" +
				"&markers=color:blue%7C" + + coordX +"," + coordY +
				"&key="+apikey);
	}
}