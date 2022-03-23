package pl.edu.pwr.lab1.i236468;

public class BMICalculatorImperial implements IBMICalculator {
	private final float weightMax;
	private final float weightMin;
	private final float heightMax;
	private final float heightMin;

	public BMICalculatorImperial(float weightMin, float weightMax, float heightMin, float heightMax){
		this.weightMax = weightMax;
		this.heightMax = heightMax;
		this.weightMin = weightMin;
		this.heightMin = heightMin;
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
		if (height == 0)
			return Float.NaN;

		return 703 * weight / (height * height);
	}}
