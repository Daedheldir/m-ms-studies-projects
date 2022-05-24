package pl.edu.pwr.lab.main_project.guided_tours;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Properties;

import pl.edu.pwr.lab.main_project.R;
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
		WebView myWebView = view.findViewById(R.id.webview);
		myWebView.getSettings().setJavaScriptEnabled(true);
		String apikey = getString(R.string.maps_api_key);

		myWebView.loadUrl("https://maps.googleapis.com/maps/api/" +
				"staticmap?center=Berkeley,CA" +
				"&zoom=14&size=400x400" +
				"&key="+apikey);
	}
}