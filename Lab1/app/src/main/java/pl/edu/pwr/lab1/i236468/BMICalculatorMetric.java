package pl.edu.pwr.lab1.i236468;

public class BMICalculatorMetric implements IBMICalculator {
	private boolean bmiCalculated = false;
	private final float weightMax;
	private final float weightMin;
	private final float heightMax;
	private final float heightMin;
	private float lastWeight = 0.0f;
	private float lastHeight = 0.0f;

	public BMICalculatorMetric(float weightMin, float weightMax, float heightMin, float heightMax){
		this.weightMax = weightMax;
		this.weightMin = weightMin;
		this.heightMax = heightMax;
		this.heightMin = heightMin;
	}

	@Override
	public boolean IsBMICalculated() {
		return bmiCalculated;
	}

	@Override
	public float GetLastWeight() {
		return lastWeight;
	}

	@Override
	public float GetLastHeight() {
		return lastHeight;
	}

	@Override
	public float GetWeightMax(){return weightMax;}
	@Override
	public float GetWeightMin(){return weightMin;}
	@Override
	public float GetHeightMax(){return heightMax;}
	@Override
	public float GetHeightMin(){return heightMin;}
	@Override
	public float CalculateBMIValue(float weight, float height) {
		bmiCalculated = false;
		if (height == 0)
			return Float.NaN;

		lastWeight = weight;
		lastHeight = height;
		bmiCalculated = true;

		return weight / ((height / 100) * (height / 100));
	}
}
