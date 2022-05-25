package pl.edu.pwr.lab.main_project.places;

import android.os.Bundle;
import android.util.TypedValue;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.ReviewsRecyclerViewAdapter;
import pl.edu.pwr.lab.main_project.WebImageDownloader;

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
		Place place = PlacesManager.getInstance().get(index);

		placeNameText.setText(place.getName());
		placeDescriptionText.setText(place.getDescription());

		addVideoToView(place);
		addImagesToView(place);
		InitializeRecyclerView(place);
		InitializeWebView(place);
	}

	private void InitializeRecyclerView(Place place) {
		RecyclerView recyclerView = findViewById(R.id.places_preview_recyclerView);
		recyclerView.setAdapter(new ReviewsRecyclerViewAdapter(place.getReviews()));
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}

	private void InitializeWebView(Place place) {
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
	private void addVideoToView(Place place){
		YouTubePlayerView youtubePlayerView = findViewById(R.id.youtube_player_view);
		getLifecycle().addObserver(youtubePlayerView);
		youtubePlayerView.setPadding(5,5,5,5);
		youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
			@Override
			public void onReady(@NonNull YouTubePlayer youTubePlayer) {
				String videoUrl = place.getVideoFilepath();
				youTubePlayer.cueVideo(videoUrl, 0);
			}
		});
	}
	private void addImagesToView(Place place) {
		List<String> imgUrls = place.getImageUrls();
		LinearLayout imagesLayout = findViewById(R.id.places_preview_imgs_layout);

		for(int i = 0; i < imgUrls.size(); ++i){
			LayoutParams imParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

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