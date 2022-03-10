package pl.edu.pwr.lab1.i236468;

public class BMICalculator {
    enum BMICategories{
        Underweight3,
        Underweight2,
        Underweight1,
        Normal,
        Overweight,
        Obese1,
        Obese2,
        Obese3
    }
    public static float CalculateBMIValue(float weight, float height){
        if(height == 0)
            return Float.MAX_VALUE;
        return weight / (height * height);
    }

    public static BMICategories GetBMICategory(float bmiIndex){
        float underweight3Max = 16.0f;
        float underweight2Max = 16.9f;
        float underweight1Max = 18.4f;
        float normalMax = 24.9f;
        float overweightMax = 29.9f;
        float obese1Max = 34.9f;
        float obese2Max = 39.9f;

        if(bmiIndex < underweight3Max) return BMICategories.Underweight3;
        if(bmiIndex < underweight2Max) return BMICategories.Underweight2;
        if(bmiIndex < underweight1Max) return BMICategories.Underweight1;
        if(bmiIndex < normalMax) return BMICategories.Normal;
        if(bmiIndex < overweightMax) return BMICategories.Overweight;
        if(bmiIndex < obese1Max) return BMICategories.Obese1;
        if(bmiIndex < obese2Max) return BMICategories.Obese2;

        return BMICategories.Obese3;
    }
}
