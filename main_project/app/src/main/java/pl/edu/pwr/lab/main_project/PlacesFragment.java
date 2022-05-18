package pl.edu.pwr.lab.main_project;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlacesFragment extends Fragment {
	private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

		private Place[] localDataSet;

		/**
		 * Provide a reference to the type of views that you are using
		 * (custom ViewHolder).
		 */
		public class ViewHolder extends RecyclerView.ViewHolder {

			public ViewHolder(View view) {
				super(view);
				// Define click listener for the ViewHolder's View

			}
		}

		public CustomAdapter() {
			Place[] dataSet = new Place[5];
			localDataSet = dataSet;
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

			// Get element from your dataset at this position and replace the
			// contents of the view with that element
			//viewHolder.getTextView().setText(localDataSet[position]);
		}

		// Return the size of your dataset (invoked by the layout manager)
		@Override
		public int getItemCount() {
			return localDataSet.length;
		}
	}

	private RecyclerView recyclerView;

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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_places, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		recyclerView = view.findViewById(R.id.placesRecyclerView);
		recyclerView.setAdapter(new CustomAdapter());
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
	}
}