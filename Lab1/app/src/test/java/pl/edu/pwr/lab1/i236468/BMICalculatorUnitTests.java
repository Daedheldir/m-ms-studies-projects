package pl.edu.pwr.lab1.i236468;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void bmiCalculationIsCorrect(){assertEquals(2.5f, BMICalculator.CalculateBMIValue(10, 2));}

    @Test
    public void bmiCategoriesAreCorrect(){
        assertEquals(BMICalculator.BMICategories.Normal, BMICalculator.GetBMICategory());
    }
}