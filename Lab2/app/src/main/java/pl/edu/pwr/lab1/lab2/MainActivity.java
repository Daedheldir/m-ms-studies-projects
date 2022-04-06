package pl.edu.pwr.lab1.lab2;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import pl.edu.pwr.lab1.lab2.layout.TasksRecyclerAdapter;

//Write atasks managerappin which:
//DONE •(1p.)  User  can  add  at  least  4  types  of  tasks  (for  example:  to-do,  email,  phone, meeting etc.)
//DONE •(2p.) Each task has:
// 		-Title,
// 		-Description,
// 		-Due date,
// 		-Status (done/ not done)
// 		-Icon (depends on type).
//DONE •(1p.) Application should have action bar with “+” action to add new task.
//DONE •(2p.) Main app screen include list of all tasks. Each list item should have icon, title, due date and status.
//DONE •(1p.) After click on the item user see new screen with all data about selected task.
//DONE •(1p.) Swipe left on item mark it as done.
//DONE •(1p.) Swipe right remove the specific item.

public class MainActivity extends AppCompatActivity {
	private RecyclerView dataRecyclerView;
	private TasksRecyclerAdapter recyclerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);
		Objects.requireNonNull(getSupportActionBar()).setTitle("Tasks Manager");
		this.getSupportActionBar().setDisplayShowTitleEnabled(true);

		dataRecyclerView = findViewById(R.id.recyclerView_main);

		ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
			@Override
			public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
				return false;
			}
			@Override
			public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
				final int position = viewHolder.getBindingAdapterPosition();
				switch (direction){
					case ItemTouchHelper.LEFT:
						recyclerAdapter.onSwipedLeft(position);
						break;
					case ItemTouchHelper.RIGHT:
						recyclerAdapter.onSwipedRight(position);
						break;
				}

				recyclerAdapter.notifyItemChanged(position);
			}
			@Override
			public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
				new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
						.addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorRed))
						.addSwipeRightActionIcon(R.drawable.ic_baseline_delete_sweep_24)
						.addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorGreen))
						.addSwipeLeftActionIcon(R.drawable.ic_baseline_check_circle_outline_24)
						.create()
						.decorate();
				super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
			}
		};

		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
		itemTouchHelper.attachToRecyclerView(dataRecyclerView);
	}
	@Override
	protected void onResume(){
		super.onResume();
		recyclerAdapter = new TasksRecyclerAdapter();

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