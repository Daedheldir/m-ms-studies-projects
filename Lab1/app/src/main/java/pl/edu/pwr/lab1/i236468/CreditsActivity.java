package pl.edu.pwr.lab1.i236468;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.Objects;

public class CreditsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);

		Toolbar toolbar = (Toolbar) findViewById(R.id.credits_toolbar);

		setSupportActionBar(toolbar);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
		getSupportActionBar().setTitle("");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == android.R.id.home) {
			Log.d("TESTING", "Credits - Pressed back button");
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}