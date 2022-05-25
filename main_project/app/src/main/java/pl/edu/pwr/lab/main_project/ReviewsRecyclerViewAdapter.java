package pl.edu.pwr.lab.main_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ViewHolder>{

		private final ArrayList<Review> reviewsList;

		/**
		 * Provide a reference to the type of views that you are using
		 * (custom ViewHolder).
		 */
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View view) {
				super(view);
				placeNameText = view.findViewById(R.id.review_row_item_textView);
				placeRatingBar = view.findViewById(R.id.review_row_item_ratingBar);

			}
			private final TextView placeNameText;
			private final RatingBar placeRatingBar;

			public TextView getPlaceNameText() {
				return placeNameText;
			}
			public RatingBar getPlaceRatingBar() {
				return placeRatingBar;
			}
		}

		public ReviewsRecyclerViewAdapter(List<Review> reviews) {
			reviewsList = new ArrayList<>(reviews);
		}

		// Create new views (invoked by the layout manager)
		@Override
		public ReviewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
			// Create a new view, which defines the UI of the list item
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.review_row_item, viewGroup, false);

			return new ReviewsRecyclerViewAdapter.ViewHolder(view);
		}

		// Replace the contents of a view (invoked by the layout manager)
		@Override
		public void onBindViewHolder(ReviewsRecyclerViewAdapter.ViewHolder viewHolder, final int position) {
			Review review = reviewsList.get(position);

			viewHolder.getPlaceNameText().setText(review.getDescription());
			viewHolder.getPlaceRatingBar().setRating(review.getRating().getStarRating());
		}


		// Return the size of your dataset (invoked by the layout manager)
		@Override
		public int getItemCount() {
			return reviewsList.size();
		}

}
