package pl.edu.pwr.lab1.lab2;

import junit.framework.TestCase;

import java.util.Date;

import pl.edu.pwr.lab1.lab2.enums.TaskTypes;

public class TaskManagerTest extends TestCase {

	public void testAddNewTask() {
		TaskManager taskManager = new TaskManager();
		Task task = new Task(TaskTypes.TODO, "Title", "Description", new Date());
		taskManager.addNewTask(task);

		assertEquals(1, taskManager.getTasksAmount());

		assertEquals(task.getTitle(), taskManager.getTaskAt(0).getTitle());
		assertEquals(task.getDescription(), taskManager.getTaskAt(0).getDescription());
		assertEquals(task.getTaskType(), taskManager.getTaskAt(0).getTaskType());
		assertEquals(task.getDueDate(), taskManager.getTaskAt(0).getDueDate());
		assertEquals(task.isTaskDone(), taskManager.getTaskAt(0).isTaskDone());
	}
}