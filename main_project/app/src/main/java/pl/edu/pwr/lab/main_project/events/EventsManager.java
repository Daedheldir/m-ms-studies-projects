package pl.edu.pwr.lab.main_project.events;

import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pwr.lab.main_project.ObjectManagerBase;

public class EventsManager extends ObjectManagerBase<Event> {
	public static EventsManager getInstance() {
		if(instance == null){
			instance = new EventsManager();
		}
		return instance;
	}
	public static EventsManager getInstance(RecyclerView recyclerView) {
		instance = getInstance();
		instance.addRecyclerViewForNotification(recyclerView);
		return instance;
	}
	private EventsManager(){
		super();
	}
	private static EventsManager instance;
}
