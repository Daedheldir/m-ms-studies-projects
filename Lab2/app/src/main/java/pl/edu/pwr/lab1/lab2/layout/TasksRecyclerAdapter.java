package pl.edu.pwr.lab1.lab2.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.lab1.lab2.R;

public class TasksRecyclerAdapter extends RecyclerView.Adapter<TasksRecyclerAdapter.ViewHolder> {
	private ArrayList<String> localDataSet;

	/**
	 * Provide a reference to the type of views that you are using
	 * (custom ViewHolder).
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final TextView textView;

		public ViewHolder(View view) {
			super(view);
			textView = view.findViewById(R.id.textView_rowItem_main);
		}

		public TextView getTextView() {
			return textView;
		}
	}

	/**
	 * Initialize the dataset of the Adapter.
	 *
	 * @param dataSet String[] containing the data to populate views to be used
	 * by RecyclerView.
	 */
	public TasksRecyclerAdapter(List<String> dataSet) {
		localDataSet = new ArrayList<>(dataSet);
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		Context context = viewGroup.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		// Inflate the custom layout
		View contactView = inflater.inflate(R.layout.row_item_recycler_view_main, viewGroup, false);

		// Return a new holder instance
		return new ViewHolder(contactView);
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		viewHolder.getTextView().setText(localDataSet.get(position));
	}

	@Override
	public int getItemCount() {
		return localDataSet.size();
	}
}
