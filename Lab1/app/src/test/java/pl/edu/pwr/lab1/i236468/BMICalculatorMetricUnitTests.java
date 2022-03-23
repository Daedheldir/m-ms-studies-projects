package pl.edu.pwr.lab1.i236468;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import pl.edu.pwr.lab1.i236468.enums.EBMICategories;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BMICalculatorMetricUnitTests {
	@Test
	public void bmiCalculationIsCorrect() {
		BMICalculatorMetric bmiCalculatorMetric = new BMICalculatorMetric(0,10,0,10);

		assertEquals(2.5f, bmiCalculatorMetric.CalculateBMIValue(10, 2.00f), 0.01f);
		assertEquals(Float.NaN, bmiCalculatorMetric.CalculateBMIValue(10, 0), 0.01f);
	}

	@Test
	public void bmiCategoriesAreCorrect() {
		BMICalculatorMetric bmiCalculatorMetric = new BMICalculatorMetric(0,10,0,10);

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
		BMICalculatorMetric bmiCalculatorMetric = new BMICalculatorMetric(0,5,0,5);
		assertEquals(bmiCalculatorMetric.IsWeightValid(bmiCalculatorMetric.GetWeightMin() - 1), -1);
		assertEquals(bmiCalculatorMetric.IsWeightValid(bmiCalculatorMetric.GetWeightMax() + 1), 1);
		assertEquals(bmiCalculatorMetric.IsWeightValid(bmiCalculatorMetric.GetWeightMin()), 0);
		assertEquals(bmiCalculatorMetric.IsWeightValid(bmiCalculatorMetric.GetWeightMax()), 0);
	}

	@Test
	public void heightValidationIsCorrect() {
		BMICalculatorMetric bmiCalculatorMetric = new BMICalculatorMetric(0,5,0,5);
		assertEquals(bmiCalculatorMetric.IsHeightValid(bmiCalculatorMetric.GetHeightMin() - 1), -1);
		assertEquals(bmiCalculatorMetric.IsHeightValid(bmiCalculatorMetric.GetHeightMax() + 1), 1);
		assertEquals(bmiCalculatorMetric.IsHeightValid(bmiCalculatorMetric.GetHeightMin()), 0);
		assertEquals(bmiCalculatorMetric.IsHeightValid(bmiCalculatorMetric.GetHeightMax()), 0);
	}
}