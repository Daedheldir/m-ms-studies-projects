package pl.edu.pwr.lab.main_project.events;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.WebImageDownloader;

public class EventPreviewActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_preview);
		TextView placeNameText = findViewById(R.id.events_preview_name_textView);
		TextView placeDescriptionText = findViewById(R.id.events_preview_description_textView);

		if(!getIntent().hasExtra("eventIndex")) {
			return;
		}

		int index = getIntent().getIntExtra("eventIndex", 0);
		Event event = EventsManager.getInstance().get(index);

		placeNameText.setText(event.getName());
		placeDescriptionText.setText(event.getDescription());

		addImagesToView(event);
		InitializeRecyclerView(event);
		InitializeWebView(event);
	}

	private void InitializeWebView(Event event) {
		WebView webView;
		webView = findViewById(R.id.events_preview_webView);

		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);

		float coordX = event.getPlace().getLocationCoords().first;
		float coordY = event.getPlace().getLocationCoords().second;

		String apikey = getString(R.string.maps_api_key);

		webView.loadUrl("https://maps.googleapis.com/maps/api/" +
				"staticmap?center=" + coordX +"," + coordY +
				"&zoom=10" +
				"&size=512x512" +
				"&markers=color:blue%7C" + + coordX +"," + coordY +
				"&key="+apikey);
	}

	private void InitializeRecyclerView(Event event) {
		RecyclerView recyclerView = findViewById(R.id.events_preview_recyclerView);
		recyclerView.setEnabled(false);
		recyclerView.setVisibility(View.GONE);
	}

	private void addImagesToView(Event event) {
		List<String> imgUrls = event.getImageUrls();
		LinearLayout imagesLayout = findViewById(R.id.events_preview_images_layout);

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