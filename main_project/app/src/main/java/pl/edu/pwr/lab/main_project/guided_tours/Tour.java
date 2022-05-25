package pl.edu.pwr.lab.main_project.guided_tours;

//	6. Guided tour should include
//		>a. Name
//		>b. Description
//		>c. List of places to visit
//		>d. Map with tour

import java.util.ArrayList;

import pl.edu.pwr.lab.main_project.places.Place;

public class Tour {
	public Tour(String name, String description, ArrayList<Place> placesList) {
		this.name = name;
		this.description = description;
		this.placesList = placesList;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<Place> getPlacesList() {
		return placesList;
	}

	private String name;
	private String description;
	private ArrayList<Place> placesList;
}
