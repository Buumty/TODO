package com.wojciechandrzejczak.TODO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TODOApplicationTests {

	@Mock
	TaskRepository taskRepository;

	@InjectMocks
	TaskService taskService;

	@Test
	public void givenTasksInDataBase_whenFindAllTasks_thenReturnAllTasks() {
		Task task1 = new Task("Do groceries", "description");
		Task task2 = new Task("Make dinner", "description");
		Task task3 = new Task("Learn English", "description");

		List<Task> taskList = new ArrayList<>();
		taskList.add(task1);
		taskList.add(task2);
		taskList.add(task3);

		when(taskRepository.findAll()).thenReturn(taskList);

		List<Task> allTasks = taskService.findAllTasks();

		assertEquals(taskList.size(), allTasks.size());
		verify(taskRepository, times(1)).findAll();
	}
	@Test
	public void givenNoTasksInDatabase_whenFindAllTasks_thenReturnEmptyList() {
		when(taskRepository.findAll()).thenReturn(Collections.emptyList());

		List<Task> allTasks = taskService.findAllTasks();

		assertEquals(0,allTasks.size());
		verify(taskRepository, times(1)).findAll();
	}
	@Test
	public void givenTaskInDatabase_whenFindAllTasks_thenReturnTaskWithCorrectDetails() {
		Task task1 = new Task("Do groceries", "description");

		ArrayList<Task> taskList = new ArrayList<>();
		taskList.add(task1);

		when(taskRepository.findAll()).thenReturn(taskList);

		List<Task> allTasks = taskService.findAllTasks();
		Task taskFromDb = allTasks.get(0);

		assertEquals(task1.getTitle(), taskFromDb.getTitle());
		assertEquals(task1.getDescription(), taskFromDb.getDescription());
		assertEquals(task1.getTaskStatus(), taskFromDb.getTaskStatus());
		assertEquals(task1.getCreatedAt(), taskFromDb.getCreatedAt());
		verify(taskRepository, times(1)).findAll();
	}

	@Test
	public void givenValidTask_whenCreateTask_thenSaveAndReturnTask() {
		Task inputTask = new Task("Do groceries", "description");

		when(taskRepository.save(any(Task.class))).thenReturn(inputTask);

		Task savedTask = taskService.createTask(inputTask);

		assertEquals(inputTask.getTitle(), savedTask.getTitle());
		assertEquals(inputTask.getDescription(), savedTask.getDescription());
		assertEquals(inputTask.getTaskStatus(), savedTask.getTaskStatus());
		assertEquals(inputTask.getCreatedAt(), savedTask.getCreatedAt());
		verify(taskRepository, times(1)).save(any(Task.class));
	}

	@Test
	public void givenNullTask_whenCreateTask_thenThrowIllegalArgumentException() {
		Task inputTask = null;

		assertThrows(IllegalArgumentException.class,
				() -> taskService.createTask(inputTask));
	}

	@Test void givenTaskWithBlankTitle_whenCreateTask_thenThrowIllegalArgumentException() {
		Task inputTask = new Task("", "description");

		assertThrows(IllegalArgumentException.class,
				() -> taskService.createTask(inputTask));
	}
}
