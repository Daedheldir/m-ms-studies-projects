package pl.edu.pwr.lab1.i236468;

import android.content.res.Resources;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import java.util.EnumMap;

import pl.edu.pwr.lab1.i236468.enums.EBMICategories;
import pl.edu.pwr.lab1.i236468.utilities.UnitConversionUtility;

public class BMICalculatorViewModel extends ViewModel {
	private final EnumMap<EBMICategories, String> bmiCategoriesLUT = new EnumMap<>(EBMICategories.class);
	private final EnumMap<EBMICategories, Integer> bmiColorsLUT = new EnumMap<>(EBMICategories.class);

	private final Resources resources;
	private IBMICalculator bmiCalculator;

	private Float lastCorrectWeight;
	private Float lastCorrectHeight;

	public BMICalculatorViewModel(final Resources resources){
		this.resources = resources;
		bmiCalculator = new BMICalculatorMetric(
				Float.parseFloat(resources.getString(R.string.weightMin_metric)),
				Float.parseFloat(resources.getString(R.string.weightMax_metric)),
				Float.parseFloat(resources.getString(R.string.heightMin_metric)),
				Float.parseFloat(resources.getString(R.string.heightMax_metric))
		);

		bmiCategoriesLUT.put(EBMICategories.Underweight3, resources.getString(R.string.underweight3Text));
		bmiCategoriesLUT.put(EBMICategories.Underweight2, resources.getString(R.string.underweight2Text));
		bmiCategoriesLUT.put(EBMICategories.Underweight1, resources.getString(R.string.underweight1Text));
		bmiCategoriesLUT.put(EBMICategories.Normal, resources.getString(R.string.normalRangeText));
		bmiCategoriesLUT.put(EBMICategories.Overweight, resources.getString(R.string.overweightText));
		bmiCategoriesLUT.put(EBMICategories.Obese1, resources.getString(R.string.obese1Text));
		bmiCategoriesLUT.put(EBMICategories.Obese2, resources.getString(R.string.obese2Text));
		bmiCategoriesLUT.put(EBMICategories.Obese3, resources.getString(R.string.obese3Text));

		bmiColorsLUT.put(EBMICategories.Underweight3, resources.getColor(R.color.bmi_underweight3));
		bmiColorsLUT.put(EBMICategories.Underweight2, resources.getColor(R.color.bmi_underweight2));
		bmiColorsLUT.put(EBMICategories.Underweight1, resources.getColor(R.color.bmi_underweight1));
		bmiColorsLUT.put(EBMICategories.Normal, resources.getColor(R.color.bmi_normalRange));
		bmiColorsLUT.put(EBMICategories.Overweight, resources.getColor(R.color.bmi_overweight));
		bmiColorsLUT.put(EBMICategories.Obese1, resources.getColor(R.color.bmi_obese1));
		bmiColorsLUT.put(EBMICategories.Obese2, resources.getColor(R.color.bmi_obese2));
		bmiColorsLUT.put(EBMICategories.Obese3, resources.getColor(R.color.bmi_obese3));
	}

	public String GetCurrentWeightUnit(){
		if(SettingsManager.IsMetricDimensions())
			return resources.getString(R.string.weightUnitsText_metric);

		return resources.getString(R.string.weightUnitsText_imperial);
	}
	public String GetCurrentHeightUnit(){
		if(SettingsManager.IsMetricDimensions())
			return resources.getString(R.string.heightUnitsText_metric);

		return resources.getString(R.string.heightUnitsText_imperial);
	}
	public void UpdateUnits(final EditText weightInputField, final TextView weightTextField, final EditText heightInputField, final TextView heightTextField){
		if(SettingsManager.IsMetricDimensions() && bmiCalculator.getClass() != BMICalculatorMetric.class){
			boolean wasBmiCalculated = lastCorrectWeight != null && lastCorrectHeight != null;

			bmiCalculator = new BMICalculatorMetric(
					Float.parseFloat(resources.getString(R.string.weightMin_metric)),
					Float.parseFloat(resources.getString(R.string.weightMax_metric)),
					Float.parseFloat(resources.getString(R.string.heightMin_metric)),
					Float.parseFloat(resources.getString(R.string.heightMax_metric)));
			//calculating bmi val to update bmi calculated flag
			if(wasBmiCalculated) {
				lastCorrectWeight = UnitConversionUtility.PoundToKilogram(lastCorrectWeight);
				lastCorrectHeight = UnitConversionUtility.InchToMeter(lastCorrectHeight);

				//conversion of last measurements from imperial to metric
				bmiCalculator.CalculateBMIValue(lastCorrectWeight, lastCorrectHeight);
				weightInputField.setText(String.valueOf(lastCorrectWeight));
				heightInputField.setText(String.valueOf(lastCorrectHeight));
			}

			weightTextField.setText(String.format("%s %s", resources.getString(R.string.weightText), resources.getString(R.string.weightUnitsText_metric)));
			heightTextField.setText(String.format("%s %s", resources.getString(R.string.heightText), resources.getString(R.string.heightUnitsText_metric)));
		}else if (!SettingsManager.IsMetricDimensions() && bmiCalculator.getClass() != BMICalculatorImperial.class){
			boolean wasBmiCalculated = lastCorrectWeight != null && lastCorrectHeight != null;

			bmiCalculator = new BMICalculatorImperial(
					Float.parseFloat(resources.getString(R.string.weightMin_imperial)),
					Float.parseFloat(resources.getString(R.string.weightMax_imperial)),
					Float.parseFloat(resources.getString(R.string.heightMin_imperial)),
					Float.parseFloat(resources.getString(R.string.heightMax_imperial)));

			//calculating bmi val to update bmi calculated flag
			if(wasBmiCalculated) {
				//conversion of last measurements from metric to imperial
				lastCorrectWeight = UnitConversionUtility.KilogramToPound(lastCorrectWeight);
				lastCorrectHeight = UnitConversionUtility.MeterToInch(lastCorrectHeight);
				bmiCalculator.CalculateBMIValue(lastCorrectWeight, lastCorrectHeight);
				weightInputField.setText(String.valueOf(lastCorrectWeight));
				heightInputField.setText(String.valueOf(lastCorrectHeight));
			}

			weightTextField.setText(String.format("%s %s", resources.getString(R.string.weightText), resources.getString(R.string.weightUnitsText_imperial)));
			heightTextField.setText(String.format("%s %s", resources.getString(R.string.heightText), resources.getString(R.string.heightUnitsText_imperial)));
		}
	}

	public boolean UpdateBMIValue(final EditText weightInputField, final EditText heightInputField, final TextView bmiOutputText){
		String weightStr = weightInputField.getText().toString();
		boolean weightValid = ValidateInputFieldNotEmpty(weightInputField, resources.getString(R.string.fieldEmptyErrorText), resources.getString(R.string.weightHintText));
		String heightStr = heightInputField.getText().toString();
		boolean heightValid = ValidateInputFieldNotEmpty(heightInputField, resources.getString(R.string.fieldEmptyErrorText), resources.getString(R.string.heightHintText));

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
			bmiOutputText.setTextColor(resources.getColor(R.color.white));

			return false;
		}

		lastCorrectWeight = Float.parseFloat(weightStr);
		lastCorrectHeight = Float.parseFloat(heightStr);
		final float bmiVal = bmiCalculator.CalculateBMIValue(lastCorrectWeight, lastCorrectHeight);

		EBMICategories bmiCategory = bmiCalculator.GetBMICategory(bmiVal);

		Integer unboxingTemp = bmiColorsLUT.get(bmiCategory);
		int bmiColor = unboxingTemp == null ? 0xFFFFFFFF : unboxingTemp;

		bmiOutputText.setText(String.valueOf(bmiVal));
		bmiOutputText.setTextColor(bmiColor);

		return true;
	}

	public boolean CanUpdatePopup(){
		return lastCorrectWeight != null && lastCorrectHeight != null;
	}

	public boolean TryUpdatePopup(final TextView popupText){
		if(!CanUpdatePopup()) return false;

		final float bmiVal = bmiCalculator.CalculateBMIValue(lastCorrectWeight, lastCorrectHeight);

		EBMICategories bmiCategory = bmiCalculator.GetBMICategory(bmiVal);
		String bmiCategoryMessage = bmiCategoriesLUT.get(bmiCategory);

		Integer unboxingTemp = bmiColorsLUT.get(bmiCategory);
		int bmiColor = unboxingTemp == null ? 0xFFFFFFFF : unboxingTemp;

		popupText.setText(String.format(resources.getString(R.string.bmiFinalText), bmiVal, bmiCategoryMessage));
		popupText.setTextColor(bmiColor);

		return true;
	}

	private boolean ValidateInputFieldNotEmpty(final TextView text, final String errorHint, final String defaultHint){
		text.setHint(defaultHint);
		text.setHintTextColor(resources.getColor(R.color.hint_gray));

		String str = text.getText().toString();
		if(str.equals("")){
			text.setHint(errorHint);
			text.setHintTextColor(resources.getColor(R.color.hint_red));
			return false;
		}
		return true;
	}
	private boolean ValidateInputFieldBounds(final int calculatorValidationOutput,
											 final float valMax,
											 final float valMin,
											 final TextView textView,
											 final int defaultMessageId,
											 final int tooLowMessageId,
											 final int tooHighMessageId ){
		boolean valValid = true;
		String newHint = resources.getString(defaultMessageId);

		if(calculatorValidationOutput == -1){
			valValid = false;
			newHint = String.format(resources.getString(tooLowMessageId), valMin);
		}else if (calculatorValidationOutput == 1){
			valValid = false;
			newHint = String.format(resources.getString(tooHighMessageId), valMax);
		}

		if(valValid){
			textView.setHintTextColor(resources.getColor(R.color.hint_gray));
			textView.setHint(resources.getString(defaultMessageId));
			return true;
		}

		textView.setHintTextColor(resources.getColor(R.color.hint_red));
		textView.setText("");
		textView.setHint(newHint);
		return false;
	}
}
