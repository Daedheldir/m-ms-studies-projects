package pl.edu.pwr.lab1.lab2.layout;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import pl.edu.pwr.lab1.lab2.R;
import pl.edu.pwr.lab1.lab2.Task;
import pl.edu.pwr.lab1.lab2.TaskManager;

public class TasksRecyclerAdapter extends RecyclerView.Adapter<TasksRecyclerAdapter.ViewHolder> {

	/**
	 * Provide a reference to the type of views that you are using
	 * (custom ViewHolder).
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final LinearLayout layout;

		private final ImageView icon;

		private final TextView textTaskType;
		private final TextView textTitle;
		private final TextView textDueDate;
		private final CheckBox checkboxTaskDone;

		public ViewHolder(View view) {
			super(view);
			layout = view.findViewById(R.id.rowItem_layout);
			icon = view.findViewById(R.id.imageView_taskIcon);
			textTaskType = view.findViewById(R.id.textView_rowItem_taskType);
			textTitle = view.findViewById(R.id.textView_rowItem_title);
			textDueDate = view.findViewById(R.id.textView_rowItem_dueDate);
			checkboxTaskDone = view.findViewById(R.id.checkBox_taskDone);
		}
		public LinearLayout getLayout() {
			return layout;
		}
		public ImageView getIcon() {
			return icon;
		}

		public TextView getTextTaskType() {
			return textTaskType;
		}

		public TextView getTextTitle() {
			return textTitle;
		}

		public TextView getTextDueDate() {
			return textDueDate;
		}

		public CheckBox getCheckBox() {
			return checkboxTaskDone;
		}
	}

	public TasksRecyclerAdapter() {
	}

	public void onSwipedRight(int position){
		TaskManager.removeTaskAt(position);
		this.notifyItemRemoved(position);
	}
	public void onSwipedLeft(int position){
		TaskManager.getTaskAt(position).setTaskDone(!TaskManager.getTaskAt(position).isTaskDone());
		this.notifyItemChanged(position);
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		Context context = viewGroup.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		// Inflate the custom layout
		View contactView = inflater.inflate(R.layout.row_item_recycler_view_main, viewGroup, false);

		// Return a new holder instance
		return new ViewHolder(contactView);
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		Task t = TaskManager.getTaskAt(position);
		viewHolder.getIcon().setImageDrawable(t.getTaskIcon());
		viewHolder.getTextTitle().setText(t.getTitle());
		viewHolder.getTextTaskType().setText(t.getTaskType().name());

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy", Locale.US);
		viewHolder.getTextDueDate().setText(formatter.format(t.getDueDate()));

		viewHolder.getCheckBox().setChecked(t.isTaskDone());

		LinearLayout rowLayout = viewHolder.getLayout();
		rowLayout.setOnClickListener(v->{
			final LayoutInflater inflater = LayoutInflater.from(viewHolder.getLayout().getContext());
			final View popupView = inflater.inflate(R.layout.task_popup, new LinearLayout(viewHolder.getLayout().getContext()));

			TextView popupTextTitle = popupView.findViewById(R.id.popup_textTitle);
			TextView popupTextDescription = popupView.findViewById(R.id.popup_textDescription);
			TextView popupTextTaskType = popupView.findViewById(R.id.popup_textTaskType);
			TextView popupTextDate = popupView.findViewById(R.id.popup_textDate);
			CheckBox popupCheckBoxTaskDone = popupView.findViewById(R.id.popup_checkBoxTaskDone);

			popupTextTitle.setText(t.getTitle());
			popupTextDescription.setText(t.getDescription());
			popupTextTaskType.setText(t.getTaskType().name());
			popupTextDate.setText(formatter.format(t.getDueDate()));
			popupCheckBoxTaskDone.setChecked(t.isTaskDone());

			final PopupWindow popupWindow = new PopupWindow(popupView,
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					true);

			popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

			popupView.setOnTouchListener((view, event) -> {
				popupWindow.dismiss();
				view.performClick();
				return true;
			});
		});
	}

	@Override
	public int getItemCount() {
		return TaskManager.getTasksAmount();
	}
}
