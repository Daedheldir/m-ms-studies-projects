package pl.edu.pwr.lab.main_project.events;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.edu.pwr.lab.main_project.R;
import pl.edu.pwr.lab.main_project.data_generators.DataGenerator;
import pl.edu.pwr.lab.main_project.places.Place;
import pl.edu.pwr.lab.main_project.places.PlacesManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {

	private class EventsCustomAdapter extends RecyclerView.Adapter<EventsCustomAdapter.ViewHolder> {

		private EventsManager localEventsManager;

		/**
		 * Provide a reference to the type of views that you are using
		 * (custom ViewHolder).
		 */
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View view) {
				super(view);
				eventLayout = view.findViewById(R.id.events_row_item_linearLayout);
				eventNameText = view.findViewById(R.id.fragment_events_name_textView);
				eventDescriptionText = view.findViewById(R.id.fragment_events_description_textView);
				webView = view.findViewById(R.id.events_row_item_webView);
				webView.getSettings().setLoadWithOverviewMode(true);
				webView.getSettings().setUseWideViewPort(true);
			}
			private final LinearLayout eventLayout;
			private final TextView eventNameText;
			private final TextView eventDescriptionText;
			private final WebView webView;

			public TextView getEventNameText() {
				return eventNameText;
			}

			public TextView getEventDescriptionText() {
				return eventDescriptionText;
			}

			public LinearLayout getEventLayout(){
				return eventLayout;
			}
			public WebView getWebView() { return webView; }

		}

		public EventsCustomAdapter(EventsManager eventsManager) {
			localEventsManager = eventsManager;
		}

		// Create new views (invoked by the layout manager)
		@Override
		public EventsCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
			// Create a new view, which defines the UI of the list item
			View view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.events_row_item, viewGroup, false);

			return new EventsCustomAdapter.ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(EventsCustomAdapter.ViewHolder viewHolder, final int position) {
			Event event = localEventsManager.get(position);

			viewHolder.getEventNameText().setText(event.getName());
			viewHolder.getEventDescriptionText().setText(event.getDescription());
			viewHolder.getEventLayout().setOnClickListener(view -> openEventPreview(view, position));

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