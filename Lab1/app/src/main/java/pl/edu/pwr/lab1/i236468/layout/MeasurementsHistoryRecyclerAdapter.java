package pl.edu.pwr.lab1.i236468.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.pwr.lab1.i236468.R;
import pl.edu.pwr.lab1.i236468.utilities.Triple;

public class MeasurementsHistoryRecyclerAdapter extends RecyclerView.Adapter<MeasurementsHistoryRecyclerAdapter.ViewHolder> {
	private ArrayList<Triple<String, String, Float>> localDataSet;

	/**
	 * Provide a reference to the type of views that you are using
	 * (custom ViewHolder).
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final Triple<TextView, TextView, TextView> textViewTriple;

		public ViewHolder(View view) {
			super(view);
			textViewTriple = new Triple<>(
					view.findViewById(R.id.recycler_row_weightTextView),
					view.findViewById(R.id.recycler_row_heightTextView),
					view.findViewById(R.id.recycler_row_bmiTextView)
					);
		}

		public Triple<TextView, TextView, TextView> getTextViewTriple() {
			return textViewTriple;
		}
	}

	/**
	 * Initialize the dataset of the Adapter.
	 *
	 * @param dataSet String[] containing the data to populate views to be used
	 * by RecyclerView.
	 */
	public MeasurementsHistoryRecyclerAdapter(ArrayList<Triple<String, String, Float>> dataSet) {
		localDataSet = dataSet;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		Context context = viewGroup.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		// Inflate the custom layout
		View contactView = inflater.inflate(R.layout.measurement_history_text_row_item, viewGroup, false);

		// Return a new holder instance
		ViewHolder viewHolder = new ViewHolder(contactView);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		viewHolder.getTextViewTriple().getFirst().setText(localDataSet.get(position).getFirst());
		viewHolder.getTextViewTriple().getSecond().setText(localDataSet.get(position).getSecond());
		viewHolder.getTextViewTriple().getThird().setText(String.valueOf(localDataSet.get(position).getThird()));
	}

	@Override
	public int getItemCount() {
		return localDataSet.size();
	}
}
