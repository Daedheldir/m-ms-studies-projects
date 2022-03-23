package pl.edu.pwr.lab1.i236468;

import java.io.Serializable;
import java.util.ArrayList;

import pl.edu.pwr.lab1.i236468.utilities.Triple;

public class MeasurementsHistoryStorage implements Serializable {
	private static ArrayList<Triple<String, String, Float>> savedMeasurements = new ArrayList<>();

	public static ArrayList<Triple<String, String, Float>> GetSavedMeasurements() {
		return savedMeasurements;
	}

	public static void AddMeasurement(final Triple<String, String, Float> newMeasurement){
		savedMeasurements.add(0, newMeasurement);
		while (savedMeasurements.size() > 3){
			savedMeasurements.remove(savedMeasurements.size() - 1);
		}
	}
}
