package pl.edu.pwr.lab.main_project.places;

import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pwr.lab.main_project.ObjectManagerBase;

public class PlacesManager extends ObjectManagerBase<Place> {
	public static PlacesManager getInstance() {
		if(instance == null){
			instance = new PlacesManager();
		}
		return instance;
	}
	public static PlacesManager getInstance(RecyclerView recyclerView) {
		instance = getInstance();
		instance.addRecyclerViewForNotification(recyclerView);
		return instance;
	}

	private PlacesManager(){
		super();
	}

	private static PlacesManager instance;
}
