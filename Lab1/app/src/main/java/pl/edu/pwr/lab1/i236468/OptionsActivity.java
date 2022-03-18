package pl.edu.pwr.lab1.i236468;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Objects;

public class OptionsActivity extends AppCompatActivity {
	private SwitchCompat switchCompat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);

		Toolbar toolbar = (Toolbar) findViewById(R.id.options_toolbar);

		setSupportActionBar(toolbar);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
		getSupportActionBar().setTitle("");

		switchCompat = findViewById(R.id.switch_units);
		switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> SettingsManager.SetMetricDimensions(!isChecked));

		Button button_authorInfo = findViewById(R.id.button_author_info);
		button_authorInfo.setOnClickListener(view -> {
			Intent intent = new Intent(this, CreditsActivity.class);
			startActivity(intent);
		});
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == android.R.id.home) {
			Log.d("TESTING", "Options - Pressed back button");
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume(){
		super.onResume();
		switchCompat.setChecked(!SettingsManager.IsMetricDimensions());
	}
}