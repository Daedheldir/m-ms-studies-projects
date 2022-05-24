package pl.edu.pwr.lab.main_project.places;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.data_generators.DataGenerator;

public class PlacesFragment extends Fragment {
	private class PlacesCustomAdapter extends RecyclerView.Adapter<PlacesCustomAdapter.ViewHolder> {

		private final PlacesManager localPlacesManager;

		/**
		 * Provide a reference to the type of views that you are using
		 * (custom ViewHolder).
		 */
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View view) {
				super(view);
				placeLayout = view.findViewById(R.id.places_row_item_linearLayout);
				placeNameText = view.findViewById(R.id.fragment_places_name_textView);
				placeRatingBar = view.findViewById(R.id.fragment_places_ratingBar);
				placeDescriptionText = view.findViewById(R.id.fragment_places_description_textView);
				webView = view.findViewById(R.id.places_row_item_webView);
				webView.getSettings().setLoadWithOverviewMode(true);
				webView.getSettings().setUseWideViewPort(true);
			}
			private final LinearLayout placeLayout;
			private final TextView placeNameText;
			private final RatingBar placeRatingBar;
			private final TextView placeDescriptionText;
			private final WebView webView;

			public TextView getPlaceNameText() {
				return placeNameText;
			}

			public TextView getPlaceDescriptionText() {
				return placeDescriptionText;
			}

			public RatingBar getPlaceRatingBar() {
				return placeRatingBar;
			}

			public LinearLayout getPlaceLayout(){
				return placeLayout;
			}

			public WebView getWebView() { return webView; }
		}

		public PlacesCustomAdapter(PlacesManager places) {
			localPlacesManager = places;
		}

		// Create new views (invoked by the layout manager)
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
			// Create a new view, which defines the UI of the list item
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.places_row_item, viewGroup, false);

			return new ViewHolder(view);
		}

		// Replace the contents of a view (invoked by the layout manager)
		@Override
		public void onBindViewHolder(ViewHolder viewHolder, final int position) {
			Place place = localPlacesManager.get(position);

			viewHolder.getPlaceNameText().setText(place.getName());
			viewHolder.getPlaceRatingBar().setRating(place.getAverageRating());
			viewHolder.getPlaceDescriptionText().setText(place.getDescription());
			viewHolder.getPlaceLayout().setOnClickListener(view -> openPlacePreview(view, position));

			float coordX = place.getLocationCoords().first;
			float coordY = place.getLocationCoords().second;
			String apikey = getString(R.string.maps_api_key);

			viewHolder.getWebView().loadUrl("https://maps.googleapis.com/maps/api/" +
					"staticmap?center=" + coordX +"," + coordY +
					"&zoom=15" +
					"&size=512x512" +
					"&markers=color:blue%7C" + + coordX +"," + coordY +
					"&key="+apikey);

		}
		private void openPlacePreview(View view, int index){
			Intent intent = new Intent(view.getContext(), PlacePreviewActivity.class);
			intent.putExtra("placeIndex", index);
			startActivity(intent);
		}

		// Return the size of your dataset (invoked by the layout manager)
		@Override
		public int getItemCount() {
			return localPlacesManager.getCount();
		}
	}


	public PlacesFragment() {
		// Required empty public constructor
	}

	public static PlacesFragment newInstance() {
		PlacesFragment fragment = new PlacesFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		placesManager = PlacesManager.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_places, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		RecyclerView recyclerView = view.findViewById(R.id.placesRecyclerView);
		placesManager.addRecyclerViewForNotification(recyclerView);
		recyclerView.setAdapter(new PlacesCustomAdapter(placesManager));
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
	}

	private PlacesManager placesManager;
}