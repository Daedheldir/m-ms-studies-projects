package pl.edu.pwr.lab1.lab2;

import android.graphics.drawable.Icon;

import java.util.Date;

import pl.edu.pwr.lab1.lab2.enums.TaskTypes;

public class Task {
	private final TaskTypes taskType;
	private final String title;
	private final String description;
	private final Date dueDate;
	private boolean taskDone;

	Task(TaskTypes taskType, final String title, final String description, Date dueDate){
		this.taskType = taskType;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.taskDone = false;
	}
	Task(TaskTypes taskType, final String title, final String description, Date dueDate, boolean taskDone){
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
}
