package pl.edu.pwr.lab.main_project.accommodations;

import android.os.Bundle;
import android.util.TypedValue;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.ReviewsRecyclerViewAdapter;
import pl.edu.pwr.lab.main_project.WebImageDownloader;

public class AccommodationPreviewActivity extends AppCompatActivity {
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
		Accommodation accommodation = AccommodationsManager.getInstance().get(index);

		placeNameText.setText(accommodation.getName());
		placeDescriptionText.setText(accommodation.getDescription());

		addImagesToView(accommodation);
		InitializeRecyclerView(accommodation);
		InitializeWebView(accommodation);
	}

	private void InitializeWebView(Accommodation accommodation) {
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

	private void InitializeRecyclerView(Accommodation accommodation) {
		RecyclerView recyclerView = findViewById(R.id.accommodations_preview_recyclerView);
		recyclerView.setAdapter(new ReviewsRecyclerViewAdapter(accommodation.getReviews()));
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}

	private void addImagesToView(Accommodation accommodation) {
		List<String> imgUrls = accommodation.getImageUrls();
		LinearLayout imagesLayout = findViewById(R.id.accommodations_preview_images_layout);

		for(int i = 0; i < imgUrls.size(); ++i){
			LinearLayout.LayoutParams imParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

			ImageView image = new ImageView(imagesLayout.getContext());
			image.setAdjustViewBounds(true);
			int imageHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 256, getResources().getDisplayMetrics()));

			image.setMaxHeight(imageHeight);
			image.setPadding(5,5,5,5);

			new WebImageDownloader(image).execute(imgUrls.get(i));

			imagesLayout.addView(image,imParams);
		}
	}
}