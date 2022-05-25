package pl.edu.pwr.lab.main_project.places;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.RecyclerViewViewHolder;

public class PlacesFragment extends Fragment {
	private class PlacesCustomAdapter extends RecyclerView.Adapter<RecyclerViewViewHolder> {

		private final PlacesManager localPlacesManager;

		public PlacesCustomAdapter(PlacesManager places) {
			localPlacesManager = places;
		}

		// Create new views (invoked by the layout manager)
		@Override
		public RecyclerViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
			// Create a new view, which defines the UI of the list item
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.recyler_row_item, viewGroup, false);

			return new RecyclerViewViewHolder(view);
		}

		// Replace the contents of a view (invoked by the layout manager)
		@Override
		public void onBindViewHolder(RecyclerViewViewHolder viewHolder, final int position) {
			Place place = localPlacesManager.get(position);

			viewHolder.getNameText().setText(place.getName());
			viewHolder.getRatingBar().setRating(place.getAverageRating());
			viewHolder.getDescriptionText().setText(place.getDescription());
			viewHolder.getLayout().setOnClickListener(view -> openPlacePreview(view, position));

			float coordX = place.getLocationCoords().first;
			float coordY = place.getLocationCoords().second;
			String apikey = getString(R.string.maps_api_key);

			viewHolder.getWebView().loadUrl("https://maps.googleapis.com/maps/api/" +
					"staticmap?center=" + coordX +"," + coordY +
					"&zoom=15" +
					"&size=512x512" +
					"&markers=color:blue%7C" + coordX +"," + coordY +
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