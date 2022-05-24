package pl.edu.pwr.lab.main_project.accommodations;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.ObjectManagerBase;
import pl.edu.pwr.lab.main_project.data_generators.DataGenerator;
import pl.edu.pwr.lab.main_project.places.Place;
import pl.edu.pwr.lab.main_project.places.PlacesManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationsFragment extends Fragment {

	private class AccommodationsCustomAdapter extends RecyclerView.Adapter<AccommodationsCustomAdapter.ViewHolder> {

		private ObjectManagerBase<Accommodation> localAccommodationsManager;

		/**
		 * Provide a reference to the type of views that you are using
		 * (custom ViewHolder).
		 */
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View view) {
				super(view);
				accommodationLayout = view.findViewById(R.id.accommodations_row_item_linearLayout);
				accommodationNameText = view.findViewById(R.id.fragment_accommodations_name_textView);
				placeRatingBar = view.findViewById(R.id.fragment_accommodations_ratingBar);
				accommodationDescriptionText = view.findViewById(R.id.fragment_accommodations_description_textView);
			}
			private final LinearLayout accommodationLayout;
			private final TextView accommodationNameText;
			private final RatingBar placeRatingBar;
			private final TextView accommodationDescriptionText;

			public TextView getAccommodationNameText() {
				return accommodationNameText;
			}

			public TextView getAccommodationDescriptionText() {
				return accommodationDescriptionText;
			}

			public RatingBar getPlaceRatingBar() {
				return placeRatingBar;
			}

			public LinearLayout getAccommodationLayout(){
				return accommodationLayout;
			}
		}

		public AccommodationsCustomAdapter(ObjectManagerBase<Accommodation> accommodationsManager) {
			localAccommodationsManager = accommodationsManager;
		}

		// Create new views (invoked by the layout manager)
		@Override
		public AccommodationsCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
			// Create a new view, which defines the UI of the list item
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.accommodations_row_item, viewGroup, false);

			return new AccommodationsCustomAdapter.ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(AccommodationsCustomAdapter.ViewHolder viewHolder, final int position) {
			Accommodation accommodation = localAccommodationsManager.get(position);

			viewHolder.getAccommodationNameText().setText(accommodation.getName());
			viewHolder.getAccommodationDescriptionText().setText(accommodation.getDescription());
			viewHolder.getPlaceRatingBar().setRating(accommodation.getAverageRating());
			viewHolder.getAccommodationLayout().setOnClickListener(view -> openAccommodationPreview(view, position));
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