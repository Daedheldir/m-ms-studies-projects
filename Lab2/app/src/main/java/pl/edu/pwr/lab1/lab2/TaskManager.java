package pl.edu.pwr.lab1.lab2;

import java.util.ArrayList;

public class TaskManager {
	static ArrayList<Task> currentTasks = new ArrayList<>();

	public static void addNewTask(Task newTask){
		currentTasks.add(newTask);
	}

	public static Task getTaskAt(int position){
		return currentTasks.get(position);
	}

	public static void removeTaskAt(int position){
		currentTasks.remove(position);
	}
	public static int getTasksAmount(){
		return currentTasks.size();
	}

}
