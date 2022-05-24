package pl.edu.pwr.lab.main_project.guided_tours;

import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pwr.lab.main_project.ObjectManagerBase;

public class ToursManager extends ObjectManagerBase<Tour> {
	public static ToursManager getInstance() {
		if(instance == null){
			instance = new ToursManager();
		}
		return instance;
	}
	public static ToursManager getInstance(RecyclerView recyclerView) {
		instance = getInstance();
		instance.addRecyclerViewForNotification(recyclerView);
		return instance;
	}
	private ToursManager(){
		super();
	}

	private static ToursManager instance;
}
