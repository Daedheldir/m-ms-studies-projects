package pl.edu.pwr.lab1.lab2;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import pl.edu.pwr.lab1.lab2.enums.TaskTypes;

public class AddTaskActivity extends AppCompatActivity {
	EditText editTextTitle;
	EditText editTextDescription;
	Spinner spinnerTaskType;
	EditText editTextDueDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);

		Toolbar toolbar = findViewById(R.id.toolbar_add_task);
		setSupportActionBar(toolbar);
		Objects.requireNonNull(getSupportActionBar()).setTitle("Add Task");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);


		//initializing input fields
		editTextTitle = findViewById(R.id.editTextTitle);
		editTextDescription = findViewById(R.id.editTextDescriptionMultiLine);

		spinnerTaskType = findViewById(R.id.spinner_taskType);
		ArrayList<String> items = new ArrayList<String>();
		for(TaskTypes taskType : TaskTypes.values()){
			items.add(taskType.name());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
		spinnerTaskType.setAdapter(adapter);

		editTextDueDate= findViewById(R.id.editTextDate);
		DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
			Calendar calendar= Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH,month);
			calendar.set(Calendar.DAY_OF_MONTH,day);
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.US);
			editTextDueDate.setText(dateFormat.format(calendar.getTime()));			};
		editTextDueDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new DatePickerDialog(
						AddTaskActivity.this,
						date,
						Calendar.getInstance().get(Calendar.YEAR),
						Calendar.getInstance().get(Calendar.MONTH),
						Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		Button addTaskButton = findViewById(R.id.button_addTask);
		addTaskButton.setOnClickListener(v -> {
			String title = editTextTitle.getText().toString();
			String description = editTextDescription.getText().toString();

			TaskTypes taskType = TaskTypes.TODO;
			try {
				taskType = TaskTypes.valueOf(spinnerTaskType.getSelectedItem().toString());
			}catch(IllegalArgumentException e){
				Log.d("ERROR", "AddTaskActivity addTaskButton onClick - taskType not found!",e);
				Toast.makeText(this, "Error! Task type not found!", Toast.LENGTH_LONG).show();
				return;
			}

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
			Date dueDate;
			try {
				dueDate = formatter.parse(editTextDueDate.getText().toString());
			} catch (ParseException e) {
				Log.d("ERROR", "AddTaskActivity addTaskButton onClick - date parse error!",e);
				Toast.makeText(this, "Error! Wrong date!", Toast.LENGTH_LONG).show();
				return;
			}
			Drawable taskIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_task_todo_24);
			switch (taskType) {
				case TODO:
					taskIcon = ContextCompat.getDrawable(this,R.drawable.ic_baseline_task_todo_24);
					break;
				case GROCERIES:
					taskIcon = ContextCompat.getDrawable(this,R.drawable.ic_baseline_task_groceries_24);
					break;
				case MEETING:
					taskIcon = ContextCompat.getDrawable(this,R.drawable.ic_baseline_task_meeting_24);
					break;
				case HEALTH:
					taskIcon = ContextCompat.getDrawable(this,R.drawable.ic_baseline_task_health_24);
					break;
			}
			addTask(taskIcon, title, description, taskType, dueDate);
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == android.R.id.home) {
			Log.d("TESTING", "Tapped back button");
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void addTask(Drawable icon, String title, String description, TaskTypes taskType, Date date){
		Task newTask = new Task(icon, taskType, title, description, date);
		TaskManager.addNewTask(newTask);
		finish();
	}
}