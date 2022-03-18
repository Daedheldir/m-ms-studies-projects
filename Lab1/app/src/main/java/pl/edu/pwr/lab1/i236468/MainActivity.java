package pl.edu.pwr.lab1.i236468;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
• (1p.) Implement layout similar to:
• (2p.) Add logic to button which valid both fields and count BMI when they
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

TODO • (1p.) Write or record (and cleanup) UI test using Espresso.

Assessment (regarding all labs)
	TODO • Code should be written according to SOLID principles.
	TODO • Code should be written in clean way (readable and without hardcoded values.
	TODO • All Strings variables which are visible for user (in UI) should be stored in strings.xml.
	TODO • Margins and paddings should be stored in dimens.xml.
	TODO • UI should be nice and clean. Material design preferred.
	TODO • Code of application should be delivered as link to github repository via email.
	TODO • Ready app should be presented during laboratories according to lab schedule.
 */
public class MainActivity extends AppCompatActivity {
	private final EnumMap<EBMICategories, String> bmiCategoriesLUT = new EnumMap<>(EBMICategories.class);
	private final EnumMap<EBMICategories, Integer> bmiColorsLUT = new EnumMap<>(EBMICategories.class);
	private IBMICalculator bmiCalculator;

	protected TextView weightText;
	protected TextView heightText;
	protected EditText weightInputField;
	protected EditText heightInputField;
	protected TextView bmiOutputText;
	protected Button calculateBMIButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("TESTING", "The onCreate() event");
		setContentView(R.layout.activity_main);

		// Attaching the layout to the toolbar object
		Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
		// Setting toolbar as the ActionBar with setSupportActionBar() call
		setSupportActionBar(toolbar);

		bmiCategoriesLUT.put(EBMICategories.Underweight3, getString(R.string.underweight3Text));
		bmiCategoriesLUT.put(EBMICategories.Underweight2, getString(R.string.underweight2Text));
		bmiCategoriesLUT.put(EBMICategories.Underweight1, getString(R.string.underweight1Text));
		bmiCategoriesLUT.put(EBMICategories.Normal, getString(R.string.normalRangeText));
		bmiCategoriesLUT.put(EBMICategories.Overweight, getString(R.string.overweightText));
		bmiCategoriesLUT.put(EBMICategories.Obese1, getString(R.string.obese1Text));
		bmiCategoriesLUT.put(EBMICategories.Obese2, getString(R.string.obese2Text));
		bmiCategoriesLUT.put(EBMICategories.Obese3, getString(R.string.obese3Text));

		bmiColorsLUT.put(EBMICategories.Underweight3, getResources().getColor(R.color.bmi_underweight3));
		bmiColorsLUT.put(EBMICategories.Underweight2, getResources().getColor(R.color.bmi_underweight2));
		bmiColorsLUT.put(EBMICategories.Underweight1, getResources().getColor(R.color.bmi_underweight1));
		bmiColorsLUT.put(EBMICategories.Normal, getResources().getColor(R.color.bmi_normalRange));
		bmiColorsLUT.put(EBMICategories.Overweight, getResources().getColor(R.color.bmi_overweight));
		bmiColorsLUT.put(EBMICategories.Obese1, getResources().getColor(R.color.bmi_obese1));
		bmiColorsLUT.put(EBMICategories.Obese2, getResources().getColor(R.color.bmi_obese2));
		bmiColorsLUT.put(EBMICategories.Obese3, getResources().getColor(R.color.bmi_obese3));

		bmiCalculator = new BMICalculatorMetric(
				Float.parseFloat(getResources().getString(R.string.weightMin_metric)),
				Float.parseFloat(getResources().getString(R.string.weightMax_metric)),
				Float.parseFloat(getResources().getString(R.string.heightMin_metric)),
				Float.parseFloat(getResources().getString(R.string.heightMax_metric)));

		weightText = findViewById(R.id.weightText);
		heightText = findViewById(R.id.heightText);
		weightText.setText(String.format("%s %s", getString(R.string.weightText), getString(R.string.weightUnitsText_metric)));
		heightText.setText(String.format("%s %s", getString(R.string.heightText), getString(R.string.heightUnitsText_metric)));

		weightInputField = findViewById(R.id.weightInputField);
		heightInputField = findViewById(R.id.heightInputField);

		bmiOutputText = findViewById(R.id.bmiValueOutputText);
		bmiOutputText.setTextColor(getResources().getColor(R.color.white));
		bmiOutputText.setClickable(true);
		bmiOutputText.setOnClickListener((v) -> {
			onButtonShowPopupWindowClick(v, getApplicationContext());
		});

		calculateBMIButton = findViewById(R.id.calculateBMIButton);
		calculateBMIButton.setOnClickListener(view -> {
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			UpdateBMIValue();
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			Log.d("TESTING", "Tapped settings button");
			OpenSettingsActivity();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart(){
		super.onStart();
		Log.d("TESTING", "The onStart() event");
	}
	@Override
	protected void onResume(){
		super.onResume();
		if(SettingsManager.IsMetricDimensions() && bmiCalculator.getClass() != BMICalculatorMetric.class){
			boolean wasBmiCalculated = bmiCalculator.IsBMICalculated();
			float lastWeight = bmiCalculator.GetLastWeight();
			float lastHeight = bmiCalculator.GetLastHeight();

			//conversion of last measurements from imperial to metric
			if(wasBmiCalculated) {
				lastWeight = UnitConversionUtility.PoundToKilogram(lastWeight);
				lastHeight = UnitConversionUtility.InchToMeter(lastWeight/100.0f);
			}

			bmiCalculator = new BMICalculatorMetric(
					Float.parseFloat(getResources().getString(R.string.weightMin_metric)),
					Float.parseFloat(getResources().getString(R.string.weightMax_metric)),
					Float.parseFloat(getResources().getString(R.string.heightMin_metric)),
					Float.parseFloat(getResources().getString(R.string.heightMax_metric)));
			//calculating bmi val to update bmi calculated flag
			if(wasBmiCalculated) {
				bmiCalculator.CalculateBMIValue(lastWeight, lastHeight);
				weightInputField.setText(String.valueOf(lastWeight));
				heightInputField.setText(String.valueOf(lastHeight));
			}

			weightText.setText(String.format("%s %s", getString(R.string.weightText), getString(R.string.weightUnitsText_metric)));
			heightText.setText(String.format("%s %s", getString(R.string.heightText), getString(R.string.heightUnitsText_metric)));
		}else if (!SettingsManager.IsMetricDimensions() && bmiCalculator.getClass() != BMICalculatorImperial.class){
			boolean wasBmiCalculated = bmiCalculator.IsBMICalculated();
			float lastWeight = bmiCalculator.GetLastWeight();
			float lastHeight = bmiCalculator.GetLastHeight();

			//conversion of last measurements from metric to imperial
			if(wasBmiCalculated) {
				lastWeight = UnitConversionUtility.KilogramToPound(lastWeight);
				lastHeight = UnitConversionUtility.MeterToInch(lastWeight/100.0f);
			}
			bmiCalculator = new BMICalculatorImperial(
					Float.parseFloat(getResources().getString(R.string.weightMin_imperial)),
					Float.parseFloat(getResources().getString(R.string.weightMax_imperial)),
					Float.parseFloat(getResources().getString(R.string.heightMin_imperial)),
					Float.parseFloat(getResources().getString(R.string.heightMax_imperial)));

			//calculating bmi val to update bmi calculated flag
			if(wasBmiCalculated) {
				bmiCalculator.CalculateBMIValue(lastWeight, lastHeight);
				weightInputField.setText(String.valueOf(lastWeight));
				heightInputField.setText(String.valueOf(lastHeight));
			}

			weightText.setText(String.format("%s %s", getString(R.string.weightText), getString(R.string.weightUnitsText_imperial)));
			heightText.setText(String.format("%s %s", getString(R.string.heightText), getString(R.string.heightUnitsText_imperial)));
		}
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

	private void onButtonShowPopupWindowClick(View view, Context context) {
		if(!bmiCalculator.IsBMICalculated()) return;

		final LayoutInflater inflater = LayoutInflater.from(context);
		final View popupView = inflater.inflate(R.layout.bmi_display_popup_window, new LinearLayout(context));
		final float bmiVal = bmiCalculator.CalculateBMIValue(bmiCalculator.GetLastWeight(), bmiCalculator.GetLastHeight());

		EBMICategories bmiCategory = bmiCalculator.GetBMICategory(bmiVal);
		String bmiCategoryMessage = bmiCategoriesLUT.get(bmiCategory);

		Integer unboxingTemp = bmiColorsLUT.get(bmiCategory);
		int bmiColor = unboxingTemp == null ? 0xFFFFFFFF : unboxingTemp;

		TextView popupText = popupView.findViewById(R.id.text_bmi_details_popup);

		popupText.setText(String.format(getString(R.string.bmiFinalText), bmiVal, bmiCategoryMessage));
		popupText.setTextColor(bmiColor);

		final PopupWindow popupWindow = new PopupWindow(popupView,
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				true);

		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

		popupView.setOnTouchListener((v, event) -> {
			popupWindow.dismiss();
			v.performClick();
			return true;
		});
	}

	private void UpdateBMIValue(){
		String weightStr = weightInputField.getText().toString();
		boolean weightValid = ValidateInputField(weightInputField, getString(R.string.fieldEmptyErrorText), getString(R.string.weightHintText));
		String heightStr = heightInputField.getText().toString();
		boolean heightValid = ValidateInputField(heightInputField, getString(R.string.fieldEmptyErrorText), getString(R.string.heightHintText));

		if(weightValid) {
			weightValid = ValidateInputFieldBounds(
					bmiCalculator.IsWeightValid(Float.parseFloat(weightStr)),
					bmiCalculator.GetWeightMax(),
					bmiCalculator.GetWeightMin(),
					weightInputField,
					R.string.weightHintText,
					R.string.fieldValueTooLowErrorText,
					R.string.fieldValueTooHighErrorText
			);
		}
		if(heightValid) {
			heightValid = ValidateInputFieldBounds(
					bmiCalculator.IsHeightValid(Float.parseFloat(heightStr)),
					bmiCalculator.GetHeightMax(),
					bmiCalculator.GetHeightMin(),
					heightInputField,
					R.string.heightHintText,
					R.string.fieldValueTooLowErrorText,
					R.string.fieldValueTooHighErrorText
			);
		}

		if(!weightValid || !heightValid){
			bmiOutputText.setText(R.string.bmiValueText);
			bmiOutputText.setTextColor(getResources().getColor(R.color.white));

			return;
		}

		final float weightVal = Float.parseFloat(weightStr);
		final float heightVal = Float.parseFloat(heightStr);
		final float bmiVal = bmiCalculator.CalculateBMIValue(weightVal, heightVal);

		EBMICategories bmiCategory = bmiCalculator.GetBMICategory(bmiVal);

		Integer unboxingTemp = bmiColorsLUT.get(bmiCategory);
		int bmiColor = unboxingTemp == null ? 0xFFFFFFFF : unboxingTemp;

		bmiOutputText.setText(String.valueOf(bmiVal));
		bmiOutputText.setTextColor(bmiColor);
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

	private boolean ValidateInputFieldBounds(final int calculatorValidationOutput, final float valMax, final float valMin, final TextView textView, int defaultMessageId, final int tooLowMessageId, final int tooHighMessageId ){
		boolean valValid = true;
		String newHint = getResources().getString(defaultMessageId);

		if(calculatorValidationOutput == -1){
			valValid = false;
			newHint = String.format(getResources().getString(tooLowMessageId), valMin);
		}else if (calculatorValidationOutput == 1){
			valValid = false;
			newHint = String.format(getResources().getString(tooHighMessageId), valMax);
		}

		if(valValid){
			textView.setHintTextColor(getResources().getColor(R.color.hint_gray));
			textView.setHint(getResources().getString(defaultMessageId));
			return true;
		}

		textView.setHintTextColor(getResources().getColor(R.color.hint_red));
		textView.setText("");
		textView.setHint(newHint);
		return false;
	}

	private void OpenSettingsActivity(){
		Intent intent = new Intent(this, OptionsActivity.class);
		startActivity(intent);
	}
}