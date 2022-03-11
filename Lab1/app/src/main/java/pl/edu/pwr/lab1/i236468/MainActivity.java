package pl.edu.pwr.lab1.i236468;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.EnumMap;

public class MainActivity extends AppCompatActivity {
	private final EnumMap<BMICalculator.BMICategories, String> bmiCategoriesLUT = new EnumMap<>(BMICalculator.BMICategories.class);
	protected EditText weightInputField;
	protected EditText heightInputField;
	protected TextView bmiOutputText;
	protected Button calculateBMIButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("TESTING", "The onCreate() event");
		setContentView(R.layout.activity_main);

		bmiCategoriesLUT.put(BMICalculator.BMICategories.Underweight3, getString(R.string.underweight3Text));
		bmiCategoriesLUT.put(BMICalculator.BMICategories.Underweight2, getString(R.string.underweight2Text));
		bmiCategoriesLUT.put(BMICalculator.BMICategories.Underweight1, getString(R.string.underweight1Text));
		bmiCategoriesLUT.put(BMICalculator.BMICategories.Normal, getString(R.string.normalRangeText));
		bmiCategoriesLUT.put(BMICalculator.BMICategories.Overweight, getString(R.string.overweightText));
		bmiCategoriesLUT.put(BMICalculator.BMICategories.Obese1, getString(R.string.obese1Text));
		bmiCategoriesLUT.put(BMICalculator.BMICategories.Obese2, getString(R.string.obese2Text));
		bmiCategoriesLUT.put(BMICalculator.BMICategories.Obese3, getString(R.string.obese3Text));

		weightInputField = findViewById(R.id.weightInputField);
		heightInputField = findViewById(R.id.heightInputField);
		bmiOutputText = findViewById(R.id.bmiValueOutputText);
		calculateBMIButton = findViewById(R.id.calculateBMIButton);

		calculateBMIButton.setOnClickListener(view -> UpdateBMIValue());

	}
	@Override
	protected void onStart(){
		super.onStart();
		Log.d("TESTING", "The onStart() event");
	}
	@Override
	protected void onResume(){
		super.onResume();
		Log.d("TESTING", "The onResume() event");
	}
	@Override
	protected void onPause(){
		super.onPause();
		Log.d("TESTING", "The onPause() event");
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d("TESTING", "The onDestroy() event");
	}
	@Override
	protected void onRestart(){
		super.onRestart();
		Log.d("TESTING", "The onRestart() event");
	}

	private void UpdateBMIValue(){
		//TODO: move all of this to bmi calculator, return from it either a string with value or error string
		String weightStr = weightInputField.getText().toString();
		boolean weightValid = ValidateInputField(weightInputField, getString(R.string.weightErrorText), getString(R.string.weightHintText));
		String heightStr = heightInputField.getText().toString();
		boolean heightValid = ValidateInputField(heightInputField, getString(R.string.heightErrorText), getString(R.string.heightHintText));

		if(!weightValid || !heightValid){
			bmiOutputText.setText(R.string.bmiValueText);
			return;
		}

		final float weightVal = Float.parseFloat(weightStr);
		final float heightVal = Float.parseFloat(heightInputField.getText().toString());

		final float bmiVal = BMICalculator.CalculateBMIValue(weightVal, heightVal/100);

		String bmiCategory = bmiCategoriesLUT.get(BMICalculator.GetBMICategory(bmiVal));

		bmiOutputText.setText(String.format(getString(R.string.bmiFinalText), bmiVal, bmiCategory));
	}

	private boolean ValidateInputField(EditText editText, String errorHint, String defaultHint){
		editText.setHint(defaultHint);
		editText.setHintTextColor(getResources().getColor(R.color.hint_gray));

		String str = editText.getText().toString();
		if(str.equals("")){
			editText.setHint(errorHint);
			editText.setHintTextColor(getResources().getColor(R.color.hint_red));
			return false;
		}
		return true;
	}
}