package pl.edu.pwr.lab.main_project.accommodations;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pwr.lab.main_project.ObjectManagerBase;
import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.RecyclerViewViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationsFragment extends Fragment {

	private class AccommodationsCustomAdapter extends RecyclerView.Adapter<RecyclerViewViewHolder> {

		private ObjectManagerBase<Accommodation> localAccommodationsManager;


		public AccommodationsCustomAdapter(ObjectManagerBase<Accommodation> accommodationsManager) {
			localAccommodationsManager = accommodationsManager;
		}

		// Create new views (invoked by the layout manager)
		@Override
		public RecyclerViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
			// Create a new view, which defines the UI of the list item
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.recyler_row_item, viewGroup, false);

			return new RecyclerViewViewHolder(view);
		}

		@Override
		public void onBindViewHolder(RecyclerViewViewHolder viewHolder, final int position) {
			Accommodation accommodation = localAccommodationsManager.get(position);

			viewHolder.getNameText().setText(accommodation.getName());
			viewHolder.getDescriptionText().setText(accommodation.getDescription());
			viewHolder.getRatingBar().setRating(accommodation.getAverageRating());
			viewHolder.getLayout().setOnClickListener(view -> openAccommodationPreview(view, position));
			viewHolder.getWebView().setEnabled(false);
			viewHolder.getWebView().setVisibility(View.GONE);
		}
		private void openAccommodationPreview(View view, int index){
			Intent intent = new Intent(view.getContext(), AccommodationPreviewActivity.class);
			intent.putExtra("accommodationIndex", index);
			startActivity(intent);
		}

		// Return the size of your dataset (invoked by the layout manager)
		@Override
		public int getItemCount() {
			return localAccommodationsManager.getCount();
		}
	}


	public AccommodationsFragment() {
		// Required empty public constructor
	}

	public static AccommodationsFragment newInstance() {
		AccommodationsFragment fragment = new AccommodationsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		accommodationsManager = AccommodationsManager.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_accommodations, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		RecyclerView recyclerView = view.findViewById(R.id.accommodationsRecyclerView);
		accommodationsManager.addRecyclerViewForNotification(recyclerView);
		recyclerView.setAdapter(new AccommodationsCustomAdapter(accommodationsManager));
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
	}

	private AccommodationsManager accommodationsManager;
}