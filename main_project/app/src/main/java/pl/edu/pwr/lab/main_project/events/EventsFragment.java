package pl.edu.pwr.lab.main_project.events;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {

	private class EventsCustomAdapter extends RecyclerView.Adapter<RecyclerViewViewHolder> {

		private final EventsManager localEventsManager;

		public EventsCustomAdapter(EventsManager eventsManager) {
			localEventsManager = eventsManager;
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
			Event event = localEventsManager.get(position);

			viewHolder.getNameText().setText(event.getName());
			viewHolder.getDescriptionText().setText(event.getDescription());

			viewHolder.getRatingBar().setEnabled(false);
			viewHolder.getRatingBar().setVisibility(View.GONE);
			viewHolder.getLayout().setOnClickListener(view -> openEventPreview(view, position));

			float coordX = event.getPlace().getLocationCoords().first;
			float coordY = event.getPlace().getLocationCoords().second;
			String apikey = getString(R.string.maps_api_key);

			viewHolder.getWebView().loadUrl("https://maps.googleapis.com/maps/api/" +
					"staticmap?center=" + coordX +"," + coordY +
					"&zoom=15" +
					"&size=512x512" +
					"&markers=color:blue%7C" + + coordX +"," + coordY +
					"&key="+apikey);
		}
		private void openEventPreview(View view, int index){
			Intent intent = new Intent(view.getContext(), EventPreviewActivity.class);
			intent.putExtra("eventIndex", index);
			startActivity(intent);
		}

		// Return the size of your dataset (invoked by the layout manager)
		@Override
		public int getItemCount() {
			return localEventsManager.getCount();
		}
	}


	public EventsFragment() {
		// Required empty public constructor
	}

	public static EventsFragment newInstance() {
		EventsFragment fragment = new EventsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		eventsManager = EventsManager.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_events, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		RecyclerView recyclerView = view.findViewById(R.id.eventsRecyclerView);
		eventsManager.addRecyclerViewForNotification(recyclerView);
		recyclerView.setAdapter(new EventsCustomAdapter(eventsManager));
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
	}

	private EventsManager eventsManager;
}