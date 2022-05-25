package pl.edu.pwr.lab.main_project.events;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwr.lab.main_project.places.Place;

//		>a. Name of event
//		>b. Event description
//		>c. Place of event (with map/direction)
//		>d. Images (up to 3)
public class Event {
	public Event(String name, Place place){
		this(name, "", place, new ArrayList<>());
	}
	public Event(String name, String description, Place place, List<String> imageUrls){
		setName(name);
		setDescription(description);
		setPlace(place);
		setImagesUrls(imageUrls);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public List<String> getImageUrls() {
		return images;
	}

	private void setImagesUrls(List<String> images){
		this.images = images;
	}

	private String name;
	private String description;
	private Place place;
	private List<String> images;
}
