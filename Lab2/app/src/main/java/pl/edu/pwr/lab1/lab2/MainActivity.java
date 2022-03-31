package pl.edu.pwr.lab1.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.edu.pwr.lab1.lab2.layout.TasksRecyclerAdapter;

public class MainActivity extends AppCompatActivity {
	private RecyclerView dataRecyclerView;
	private TasksRecyclerAdapter recyclerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Tasks Manager");
		this.getSupportActionBar().setDisplayShowTitleEnabled(true);

		dataRecyclerView = findViewById(R.id.recyclerView_main);
	}
	@Override
	protected void onResume(){
		super.onResume();
		recyclerAdapter = new TasksRecyclerAdapter(Arrays.asList("Task1", "Task2", "Task3"));

		dataRecyclerView.setAdapter(recyclerAdapter);
		dataRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
		if (id == R.id.action_add_task) {
			Log.d("TESTING", "Tapped add button");
			OpenAddTaskActivity();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void OpenAddTaskActivity() {
		Intent intent = new Intent(this, AddTaskActivity.class);
		startActivity(intent);
	}
}