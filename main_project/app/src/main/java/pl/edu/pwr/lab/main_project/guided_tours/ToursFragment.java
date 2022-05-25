package pl.edu.pwr.lab.main_project.guided_tours;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.RecyclerViewViewHolder;
import pl.edu.pwr.lab.main_project.places.Place;


public class ToursFragment extends Fragment {
	private class ToursCustomAdapter extends RecyclerView.Adapter<RecyclerViewViewHolder> {

		private final ToursManager localToursManager;

		public ToursCustomAdapter(ToursManager tours) {
			localToursManager = tours;
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
			Tour tour = localToursManager.get(position);

			viewHolder.getNameText().setText(tour.getName());
			viewHolder.getDescriptionText().setText(tour.getDescription());

			viewHolder.getRatingBar().setEnabled(false);
			viewHolder.getRatingBar().setVisibility(View.GONE);

			String apikey = getString(R.string.maps_api_key);

			ArrayList<Pair<Float,Float>> placesCoords = new ArrayList<>();

			for(Place place : ToursManager.getInstance().get(position).getPlacesList()){
				placesCoords.add(place.getLocationCoords());
			}

			StringBuilder placesCoordsStr = new StringBuilder();

			ArrayList<Pair<Float, Float>> tourCoordsOrganized = organizeCoordsByClosestRemainingNeighbour(placesCoords);

			for(Pair<Float, Float> place : tourCoordsOrganized) {
				placesCoordsStr.append(place.first).append(",").append(place.second).append("|");
			}

			placesCoordsStr.deleteCharAt(placesCoordsStr.lastIndexOf("|"));

			viewHolder.getWebView().loadUrl("https://maps.googleapis.com/maps/api/" +
					"staticmap?" +
					"&size=1024x1024" +
					"&markers=color:blue%7C" + placesCoordsStr +
					"&path=weight:5|color:0xAAAAFFCC|geodesic:true|" + placesCoordsStr +
					"&key=" + apikey);
		}

		// Return the size of your dataset (invoked by the layout manager)
		@Override
		public int getItemCount() {
			return localToursManager.getCount();
		}

		private ArrayList<Pair<Float, Float>> organizeCoordsByClosestRemainingNeighbour(List<Pair<Float, Float>> placesCoords){
			ArrayList<Pair<Float, Float>> tourCoordsOrganized = new ArrayList<>();

			tourCoordsOrganized.add(placesCoords.get(0));

			//finding closest places to connect them with lines on map
			for(int i =0; i < placesCoords.size()-1; ++i){
				Pair<Float, Float> currentTour = tourCoordsOrganized.get(tourCoordsOrganized.size() -1);
				Pair<Float, Float> closestTour = placesCoords.get(0);

				float lowestDistance = Float.MAX_VALUE;

				for(int j = 0; j < placesCoords.size(); ++j){
					Pair<Float, Float> testedTour = placesCoords.get(j);

					if(currentTour == testedTour)
						continue;

					float distance = (float)Math.sqrt(Math.pow(currentTour.first - testedTour.first, 2) + Math.pow(currentTour.second - testedTour.second, 0));

					if(distance < lowestDistance && !tourCoordsOrganized.contains(testedTour)){
						lowestDistance = distance;
						closestTour = testedTour;
					}
				}
				tourCoordsOrganized.add(closestTour);
			}

			return tourCoordsOrganized;
		}
	}


	public ToursFragment() {
		// Required empty public constructor
	}

	public static ToursFragment newInstance() {
		ToursFragment fragment = new ToursFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		toursManager = ToursManager.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_tours, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		RecyclerView recyclerView = view.findViewById(R.id.toursRecyclerView);
		toursManager.addRecyclerViewForNotification(recyclerView);
		recyclerView.setAdapter(new ToursFragment.ToursCustomAdapter(toursManager));
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
	}

	private ToursManager toursManager;
}