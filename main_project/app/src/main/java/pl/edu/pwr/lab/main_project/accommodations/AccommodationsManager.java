package pl.edu.pwr.lab.main_project.accommodations;

import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pwr.lab.main_project.ObjectManagerBase;

public class AccommodationsManager extends ObjectManagerBase<Accommodation> {
	public static AccommodationsManager getInstance() {
		if(instance == null){
			instance = new AccommodationsManager();
		}
		return instance;
	}
	public static AccommodationsManager getInstance(RecyclerView recyclerView) {
		instance = getInstance();
		instance.addRecyclerViewForNotification(recyclerView);
		return instance;
	}
	private AccommodationsManager(){
		super();
	}

	private static AccommodationsManager instance;
}
