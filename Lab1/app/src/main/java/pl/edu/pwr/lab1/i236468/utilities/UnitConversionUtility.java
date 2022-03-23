package pl.edu.pwr.lab1.i236468.utilities;

public class UnitConversionUtility {
	private UnitConversionUtility(){};

	public static float InchToMeter(final float inches){
		return inches * 0.0254f;
	}
	public static float MeterToInch(final float meters){
		return meters / 0.0254f;
	}

	public static float PoundToKilogram(final float pounds){
		return pounds * 0.45359237f;
	}
	public static float KilogramToPound(final float kilograms){
		return kilograms / 0.45359237f;
	}
}
