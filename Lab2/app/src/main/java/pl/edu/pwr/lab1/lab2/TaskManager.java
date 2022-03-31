package pl.edu.pwr.lab1.lab2;

import java.util.ArrayList;

public class TaskManager {
	ArrayList<Task> currentTasks;

	public TaskManager(){
		currentTasks = new ArrayList<>();
	}

	public void addNewTask(Task newTask){
		currentTasks.add(newTask);
	}

	public final Task getTaskAt(int position){
		return currentTasks.get(position);
	}

	public final int getTasksAmount(){
		return currentTasks.size();
	}

}
