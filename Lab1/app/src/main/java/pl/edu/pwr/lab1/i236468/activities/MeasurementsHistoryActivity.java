package pl.edu.pwr.lab1.i236468.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import pl.edu.pwr.lab1.i236468.MeasurementsHistoryStorage;
import pl.edu.pwr.lab1.i236468.R;
import pl.edu.pwr.lab1.i236468.layout.MeasurementsHistoryRecyclerAdapter;

public class MeasurementsHistoryActivity extends AppCompatActivity {

	private RecyclerView dataRecyclerView;
	private MeasurementsHistoryRecyclerAdapter recyclerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_measurements_history);

		Toolbar toolbar = (Toolbar) findViewById(R.id.savedMeasure_toolbar);

		setSupportActionBar(toolbar);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
		getSupportActionBar().setTitle(R.string.measurements_history_toolbarTitle);

		dataRecyclerView = findViewById(R.id.recyclerView_measurements_history);
	}
	@Override
	protected void onResume(){
		super.onResume();
		recyclerAdapter = new MeasurementsHistoryRecyclerAdapter(MeasurementsHistoryStorage.GetSavedMeasurements());

		dataRecyclerView.setAdapter(recyclerAdapter);
		dataRecyclerView.setLayoutManager(new LinearLayoutManager(this));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == android.R.id.home) {
			Log.d("TESTING", "Measurements History - Pressed back button");
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}