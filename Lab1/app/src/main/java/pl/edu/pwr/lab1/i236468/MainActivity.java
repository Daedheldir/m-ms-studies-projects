package pl.edu.pwr.lab1.i236468;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.EnumMap;

/* TODO ASSIGNMENT
>1. (1 p.) Write app which use all methods from activity lifecycle and log them using
logcat Log.d(). Example:
 @Override
 protected void onStart() {
 super.onStart();
 Log.d("TAG", "The onStart() event");
 }
Check their behavior for actions like switching the app, rotation and others typical
for phone user.

2. Write BMI application:
The app collect data about mass and height and allow user to count his/her BMI.
>• (1p.) Implement layout similar to:
>• (2p.) Add logic to button which valid both fields and count BMI when they
	are correct or shows error in other case. BMI counter should be separate
	class which is used in activity, it can also validate values of mass and height.
	Hint: EditText has inputType attribute which allow to define what
	kinds of data user can put in, for example number.

• (2p.) Add options menu (onCreateOptionsMenu() in Activity), which
	allow to switch between metric and imperial type of data. Add required
	support.

• (1p.) Add option in menu which shows information about author in
	separate activity.

• (1p.) Text field which display BMI should change the color according to
	BMI classification (normal, overweight, etc.)

• (2p.) After click on BMI_VALUE text field user should see new activity with
	BMI value and description according to BMI classification.
	Hint: use startActivityForResult() to share value of BMI with new
	activity

• (1p.) Write unit test for both BMI counters (imperial and metric). You can
	use KotlinTest or Junit.

• (1p.) Write or record (and cleanup) UI test using Espresso.

Assessment (regarding all labs)
	• Code should be written according to SOLID principles.
	• Code should be written in clean way (readable and without hardcoded values.
	• All Strings variables which are visible for user (in UI) should be stored in
	strings.xml.
	• Margins and paddings should be stored in dimens.xml.
	• UI should be nice and clean. Material design preferred.
	• Code of application should be delivered as link to github repository via email.
	• Ready app should be presented during laboratories according to lab schedule.
 */
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
		final float heightVal = Float.parseFloat(heightStr);

		if(!BMICalculator.IsHeightValid(heightVal) || !BMICalculator.IsWeightValid(weightVal)){
			bmiOutputText.setText(R.string.bmiValueText);
			return;
		}

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