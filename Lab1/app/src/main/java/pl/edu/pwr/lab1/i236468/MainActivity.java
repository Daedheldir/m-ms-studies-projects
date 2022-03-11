package pl.edu.pwr.lab1.i236468;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	protected EditText weightInputField;
	protected EditText heightInputField;
	protected TextView bmiOutputText;
	protected Button calculateBMIButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("TESTING", "The onCreate() event");
		setContentView(R.layout.activity_main);

		weightInputField = (EditText)findViewById(R.id.weightInputField);
		heightInputField = (EditText)findViewById(R.id.heightInputField);
		bmiOutputText = (TextView)findViewById(R.id.bmiValueOutputText);
		calculateBMIButton = (Button)findViewById(R.id.calculateBMIButton);

		calculateBMIButton.setOnClickListener(view -> UpdateBMIValue());

    	/*TextWatcher inputWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                UpdateBMIValue();
            }
        };

        weightInputField.addTextChangedListener(inputWatcher);
        heightInputField.addTextChangedListener(inputWatcher);
        */
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
		String weightStr = weightInputField.getText().toString();
		if(weightStr.equals("")){
			bmiOutputText.setText(R.string.bmiValueText);
			weightInputField.setHint(R.string.weightErrorText);
			weightInputField.setHintTextColor(getResources().getColor(R.color.red));
			return;
		}

		String heightStr = heightInputField.getText().toString();
		if(heightStr.equals("")){
			bmiOutputText.setText(R.string.bmiValueText);
			heightInputField.setHint(R.string.heightErrorText);
			heightInputField.setHintTextColor(getResources().getColor(R.color.red));
			return;
		}

		weightInputField.setHint(R.string.weightHintText);
		weightInputField.setHintTextColor(getResources().getColor(R.color.black));
		heightInputField.setHint(R.string.heightHintText);
		heightInputField.setHintTextColor(getResources().getColor(R.color.black));

		final float weightVal = Float.parseFloat(weightStr);
		final float heightVal = Float.parseFloat(heightInputField.getText().toString());

		final float bmiVal = BMICalculator.CalculateBMIValue(weightVal, heightVal/100);

		String bmiCategory = "";

		switch (BMICalculator.GetBMICategory(bmiVal)){
			case Underweight3:
				bmiCategory = getString(R.string.underweight3Text);
				break;
			case Underweight2:
				bmiCategory = getString(R.string.underweight2Text);
				break;
			case Underweight1:
				bmiCategory = getString(R.string.underweight1Text);
				break;
			case Normal:
				bmiCategory = getString(R.string.normalRangeText);
				break;
			case Overweight:
				bmiCategory = getString(R.string.overweightText);
				break;
			case Obese1:
				bmiCategory = getString(R.string.obese1Text);
				break;
			case Obese2:
				bmiCategory = getString(R.string.obese2Text);
				break;
			case Obese3:
				bmiCategory = getString(R.string.obese3Text);
				break;
		}
		bmiOutputText.setText(String.format(getString(R.string.bmiFinalText), String.valueOf(bmiVal), bmiCategory));
	}
}