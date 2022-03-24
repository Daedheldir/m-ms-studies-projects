package pl.edu.pwr.lab1.i236468.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

import pl.edu.pwr.lab1.i236468.BMICalculatorViewModel;
import pl.edu.pwr.lab1.i236468.MeasurementsHistoryStorage;
import pl.edu.pwr.lab1.i236468.R;
import pl.edu.pwr.lab1.i236468.utilities.Triple;

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
	private BMICalculatorViewModel bmiCalculatorViewModel;

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

		bmiCalculatorViewModel = new BMICalculatorViewModel(getResources());

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
			boolean updateSuccess = bmiCalculatorViewModel.UpdateBMIValue(weightInputField, heightInputField, bmiOutputText);

			if(updateSuccess) {
				MeasurementsHistoryStorage.AddMeasurement(new Triple<>(
						weightInputField.getText().toString() + " " + bmiCalculatorViewModel.GetCurrentWeightUnit(),
						heightInputField.getText().toString() + " " + bmiCalculatorViewModel.GetCurrentHeightUnit(),
						Float.parseFloat(bmiOutputText.getText().toString())
				));
			}
		});

		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.savedMeasurementsFileKey), Context.MODE_PRIVATE);
		int savedMeasurementsAmount = sharedPref.getInt(getString(R.string.savedMeasurementsFileAmount), -1);

		MeasurementsHistoryStorage.ClearSavedMeasurements();
		//loading from last to first, because adding to keep things in order after adding to measurements history
		for(int i = savedMeasurementsAmount-1; i >= 0; --i){
			String weight = sharedPref.getString(getString(R.string.savedMeasurementsFileWeightKey) + String.valueOf(i), "-1.0");
			String height = sharedPref.getString(getString(R.string.savedMeasurementsFileHeightKey) + String.valueOf(i), "-1.0");
			float bmi = sharedPref.getFloat(getString(R.string.savedMeasurementsFileBMIKey) + String.valueOf(i), -1.0f);

			if(weight.equals("-1.0") || height.equals("-1.0") || bmi == -1.0f)
				continue;

			MeasurementsHistoryStorage.AddMeasurement(new Triple<>(
					weight,
					height,
					bmi
			));
		}
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
		bmiCalculatorViewModel.UpdateUnits(weightInputField, weightText, heightInputField, heightText);

		Log.d("TESTING", "The onResume() event");
	}
	@Override
	protected void onPause(){
		super.onPause();
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.savedMeasurementsFileKey), Context.MODE_PRIVATE);

		final ArrayList<Triple<String, String, Float>> savedMeasurements = MeasurementsHistoryStorage.GetSavedMeasurements();
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(getString(R.string.savedMeasurementsFileAmount), savedMeasurements.size());

		for(int i = 0; i < savedMeasurements.size(); ++i){
			String weight = savedMeasurements.get(i).getFirst();
			String height = savedMeasurements.get(i).getSecond();
			Float bmi = savedMeasurements.get(i).getThird();

			editor.putString(getString(R.string.savedMeasurementsFileWeightKey) + String.valueOf(i), weight);
			editor.putString(getString(R.string.savedMeasurementsFileHeightKey) + String.valueOf(i), height);
			editor.putFloat(getString(R.string.savedMeasurementsFileBMIKey) + String.valueOf(i), bmi);
			Log.d("TESTING", "Saved measurements item " + String.valueOf(i));
		}

		editor.apply();

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
		if(!bmiCalculatorViewModel.CanUpdatePopup()) return;

		final LayoutInflater inflater = LayoutInflater.from(context);
		final View popupView = inflater.inflate(R.layout.bmi_display_popup_window, new LinearLayout(context));

		TextView popupText = popupView.findViewById(R.id.text_bmi_details_popup);

		if(!bmiCalculatorViewModel.TryUpdatePopup(popupText)) return;

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


	private void OpenSettingsActivity(){
		Intent intent = new Intent(this, OptionsActivity.class);
		startActivity(intent);
	}
}