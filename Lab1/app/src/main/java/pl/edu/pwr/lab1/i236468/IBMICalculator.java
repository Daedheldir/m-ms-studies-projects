package pl.edu.pwr.lab1.i236468;

import pl.edu.pwr.lab1.i236468.enums.EBMICategories;

public interface IBMICalculator {
	float GetWeightMax();
	float GetWeightMin();
	float GetHeightMax();
	float GetHeightMin();
	/**
	 * @return Returns -1 if weight is lower than minimum, 1 if its bigger than maximum and 0 if it's valid
	 */
	default int IsWeightValid(float weight){
		if(weight < GetWeightMin()) return -1;
		if(weight > GetWeightMax()) return 1;
		return 0;
	}

	/**
	 * @return Returns -1 if weight is lower than minimum, 1 if its bigger than maximum and 0 if it's valid
	 */
	default int IsHeightValid(float height){
		if(height < GetHeightMin()) return -1;
		if(height > GetHeightMax()) return 1;
		return 0;
	}
	float CalculateBMIValue(float weight, float height);

	default EBMICategories GetBMICategory(float bmiIndex){
		float underweight3Max = 16.0f;
		float underweight2Max = 16.9f;
		float underweight1Max = 18.4f;
		float normalMax = 24.9f;
		float overweightMax = 29.9f;
		float obese1Max = 34.9f;
		float obese2Max = 39.9f;

		if(bmiIndex < underweight3Max) return EBMICategories.Underweight3;
		if(bmiIndex < underweight2Max) return EBMICategories.Underweight2;
		if(bmiIndex < underweight1Max) return EBMICategories.Underweight1;
		if(bmiIndex < normalMax) return EBMICategories.Normal;
		if(bmiIndex < overweightMax) return EBMICategories.Overweight;
		if(bmiIndex < obese1Max) return EBMICategories.Obese1;
		if(bmiIndex < obese2Max) return EBMICategories.Obese2;

		return EBMICategories.Obese3;
	}
}
