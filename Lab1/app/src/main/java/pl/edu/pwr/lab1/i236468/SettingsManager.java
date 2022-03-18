package pl.edu.pwr.lab1.i236468;

public class SettingsManager {
	private static boolean metricDimensions = true;

	private SettingsManager(){};

	public static boolean IsMetricDimensions() {
		return metricDimensions;
	}
	public static void SetMetricDimensions(boolean newMetricDimensions) {
		metricDimensions = newMetricDimensions;
	}
}
