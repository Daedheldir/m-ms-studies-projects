package pl.edu.pwr.lab1.i236468;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BMICalculatorUnitTests {
	@Test
	public void bmiCalculationIsCorrect(){
		assertEquals(2.5f, BMICalculator.CalculateBMIValue(10, 2), 0.01f);
		assertEquals(Float.MAX_VALUE, BMICalculator.CalculateBMIValue(10, 0), 0.01f);
	}

	@Test
	public void bmiCategoriesAreCorrect(){
		assertEquals(BMICalculator.BMICategories.Underweight3, BMICalculator.GetBMICategory(0.0f));

		assertNotEquals(BMICalculator.BMICategories.Underweight3, BMICalculator.GetBMICategory(16.0f));
		assertEquals(BMICalculator.BMICategories.Underweight2, BMICalculator.GetBMICategory(16.0f));
		assertEquals(BMICalculator.BMICategories.Underweight1, BMICalculator.GetBMICategory(17.0f));

		assertEquals(BMICalculator.BMICategories.Normal, BMICalculator.GetBMICategory(18.5f));
		assertEquals(BMICalculator.BMICategories.Overweight, BMICalculator.GetBMICategory(25.0f));

		assertEquals(BMICalculator.BMICategories.Obese1, BMICalculator.GetBMICategory(30.0f));
		assertEquals(BMICalculator.BMICategories.Obese2, BMICalculator.GetBMICategory(35.0f));
		assertEquals(BMICalculator.BMICategories.Obese3, BMICalculator.GetBMICategory(40.0f));

		assertEquals(BMICalculator.BMICategories.Obese3, BMICalculator.GetBMICategory(Float.MAX_VALUE));
	}
}