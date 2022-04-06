package pl.edu.pwr.lab1.lab2;

import android.graphics.drawable.Drawable;

import java.util.Date;

import pl.edu.pwr.lab1.lab2.enums.TaskTypes;

public class Task {
	private final Drawable taskIcon;
	private final TaskTypes taskType;
	private final String title;
	private final String description;
	private final Date dueDate;
	private boolean taskDone;

	Task(Drawable taskIcon, TaskTypes taskType, final String title, final String description, Date dueDate){
		this(taskIcon, taskType, title, description, dueDate, false);
	}
	Task(Drawable taskIcon, TaskTypes taskType, final String title, final String description, Date dueDate, boolean taskDone){
		this.taskIcon = taskIcon;
		this.taskType = taskType;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.taskDone = taskDone;
	}

	public TaskTypes getTaskType() {
		return taskType;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public boolean isTaskDone() {
		return taskDone;
	}

	public void setTaskDone(boolean taskDone) {
		this.taskDone = taskDone;
	}

	public Drawable getTaskIcon() {
		return taskIcon;
	}
}
