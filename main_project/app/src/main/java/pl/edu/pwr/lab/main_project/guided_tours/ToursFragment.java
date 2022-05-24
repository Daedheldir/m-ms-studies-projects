package pl.edu.pwr.lab.main_project.guided_tours;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.places.Place;
import pl.edu.pwr.lab.main_project.places.PlacesFragment;


public class ToursFragment extends Fragment {
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

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_tours, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		WebView webView = view.findViewById(R.id.webview);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);

		String apikey = getString(R.string.maps_api_key);

		ArrayList<Pair<Float,Float>> placesCoords = new ArrayList<>();
		for(Place place : ToursManager.getInstance().get(0).getPlacesList()){
			placesCoords.add(place.getLocationCoords());
		}

		StringBuilder placesCoordsStr = new StringBuilder();
		ArrayList<Pair<Float, Float>> tourCoordsOrganized = new ArrayList<>();

		tourCoordsOrganized.add(placesCoords.get(0));

		for(int i =0; i < placesCoords.size()-1; ++i){
			Pair<Float, Float> currentPlace = tourCoordsOrganized.get(tourCoordsOrganized.size() -1);
			Pair<Float, Float> closestPlace = placesCoords.get(0);

			float lowestDistance = Float.MAX_VALUE;

			for(int j = 0; j < placesCoords.size(); ++j){
				Pair<Float, Float> testedPlace = placesCoords.get(j);

				if(currentPlace == testedPlace) continue;

				float distance = (float)Math.sqrt(Math.pow(currentPlace.first - testedPlace.first, 2) + Math.pow(currentPlace.second - testedPlace.second, 0));

				if(distance < lowestDistance && !tourCoordsOrganized.contains(testedPlace)){
					lowestDistance = distance;
					closestPlace = testedPlace;
				}
			}
			tourCoordsOrganized.add(closestPlace);
		}

		for(Pair<Float, Float> place : tourCoordsOrganized) {
			placesCoordsStr.append(place.first).append(",").append(place.second).append("|");
		}

		placesCoordsStr.deleteCharAt(placesCoordsStr.lastIndexOf("|"));

		webView.loadUrl("https://maps.googleapis.com/maps/api/" +
				"staticmap?" +
				"&size=512x512" +
				"&markers=color:blue%7C" + placesCoordsStr.toString() +
				"&path=weight:5|color:0xAAAAFFCC|geodesic:true|" + placesCoordsStr.toString() +
				"&key="+apikey);
	}
}