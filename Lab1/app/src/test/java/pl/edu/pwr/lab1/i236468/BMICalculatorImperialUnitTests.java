package pl.edu.pwr.lab1.i236468;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import pl.edu.pwr.lab1.i236468.enums.EBMICategories;
import pl.edu.pwr.lab1.i236468.utilities.UnitConversionUtility;

public class BMICalculatorImperialUnitTests {
	@Test
	public void bmiCalculationIsCorrect() {
		BMICalculatorImperial bmiCalculatorMetric = new BMICalculatorImperial(0,10,0,10);

		float weight = UnitConversionUtility.KilogramToPound(10.0f);
		float height = UnitConversionUtility.MeterToInch(2.0f);

		assertEquals(2.5f, bmiCalculatorMetric.CalculateBMIValue(weight, height), 0.01f);
		assertEquals(Float.NaN, bmiCalculatorMetric.CalculateBMIValue(weight, 0), 0.01f);
	}

	@Test
	public void bmiCategoriesAreCorrect() {
		BMICalculatorImperial bmiCalculatorMetric = new BMICalculatorImperial(0,10,0,10);

		assertEquals(EBMICategories.Underweight3, bmiCalculatorMetric.GetBMICategory(0.0f));

		assertNotEquals(EBMICategories.Underweight3, bmiCalculatorMetric.GetBMICategory(16.0f));
		assertEquals(EBMICategories.Underweight2, bmiCalculatorMetric.GetBMICategory(16.0f));
		assertEquals(EBMICategories.Underweight1, bmiCalculatorMetric.GetBMICategory(17.0f));

		assertEquals(EBMICategories.Normal, bmiCalculatorMetric.GetBMICategory(18.5f));
		assertEquals(EBMICategories.Overweight, bmiCalculatorMetric.GetBMICategory(25.0f));

		assertEquals(EBMICategories.Obese1, bmiCalculatorMetric.GetBMICategory(30.0f));
		assertEquals(EBMICategories.Obese2, bmiCalculatorMetric.GetBMICategory(35.0f));
		assertEquals(EBMICategories.Obese3, bmiCalculatorMetric.GetBMICategory(40.0f));

		assertEquals(EBMICategories.Obese3, bmiCalculatorMetric.GetBMICategory(Float.MAX_VALUE));
	}


	@Test
	public void weightValidationIsCorrect() {
		BMICalculatorImperial bmiCalculatorMetric = new BMICalculatorImperial(0,5,0,5);
		assertEquals(bmiCalculatorMetric.IsWeightValid(bmiCalculatorMetric.GetWeightMin() - 1), -1);
		assertEquals(bmiCalculatorMetric.IsWeightValid(bmiCalculatorMetric.GetWeightMax() + 1), 1);
		assertEquals(bmiCalculatorMetric.IsWeightValid(bmiCalculatorMetric.GetWeightMin()), 0);
		assertEquals(bmiCalculatorMetric.IsWeightValid(bmiCalculatorMetric.GetWeightMax()), 0);
	}

	@Test
	public void heightValidationIsCorrect() {
		BMICalculatorImperial bmiCalculatorMetric = new BMICalculatorImperial(0,5,0,5);
		assertEquals(bmiCalculatorMetric.IsHeightValid(bmiCalculatorMetric.GetHeightMin() - 1), -1);
		assertEquals(bmiCalculatorMetric.IsHeightValid(bmiCalculatorMetric.GetHeightMax() + 1), 1);
		assertEquals(bmiCalculatorMetric.IsHeightValid(bmiCalculatorMetric.GetHeightMin()), 0);
		assertEquals(bmiCalculatorMetric.IsHeightValid(bmiCalculatorMetric.GetHeightMax()), 0);
	}
}
