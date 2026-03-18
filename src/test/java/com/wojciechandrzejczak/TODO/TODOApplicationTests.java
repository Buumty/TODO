package com.wojciechandrzejczak.TODO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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
	void givenValidId_whenFindTaskById_thenReturnTask() {
		Task existingTask = new Task("Do groceries", "description");
		existingTask.setId(1L);

		when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

		Task taskFromDb = taskService.findTaskById(1L);

		verify(taskRepository, times(1)).findById(1L);
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

	@Test
	void givenValidNewTask_whenUpdateTask_thenReturnUpdatedTask() {
		Task exsitingTask = new Task("Do groceries", "Go to teh shop and make groceries for today's dinner");
		exsitingTask.setId(1L);

		Task newTask = new Task("Make dinner", "Cook delicious meal");

		when(taskRepository.findById(1L)).thenReturn(Optional.of(exsitingTask));
		when(taskRepository.save(any(Task.class))).thenReturn(newTask);

		Task updatedTask = taskService.updateTask(1L, newTask);

		assertEquals(newTask.getId(), updatedTask.getId());
		assertEquals(newTask.getTitle(), updatedTask.getTitle());
		assertEquals(newTask.getDescription(), updatedTask.getDescription());
		assertEquals(newTask.getTaskStatus(), updatedTask.getTaskStatus());
		assertEquals(newTask.getCreatedAt(), updatedTask.getCreatedAt());
		verify(taskRepository,times(1)).save(any(Task.class));
		verify(taskRepository, times(1)).findById(1L);
	}

	@Test
	void givenNonExistingId_whenUpdateTask_thenThrowNoSuchElementException() {
		Long nonExistingId = 1000L;

		Task newTask = new Task("Make dinner", "Cook delicious meal");

		when(taskRepository.findById(nonExistingId)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class,
				() -> taskService.updateTask(nonExistingId, newTask));

		verify(taskRepository, times(1)).findById(nonExistingId);
	}

	@Test
	void givenTaskWithBlankTitle_whenUpdateTask_thenReturnIllegalArgumentException() {
		Task exsitingTask = new Task("Do groceries", "Go to teh shop and make groceries for today's dinner");
		exsitingTask.setId(1L);

		Task newTask = new Task("", "Cook delicious meal");

		when(taskRepository.findById(1L)).thenReturn(Optional.of(exsitingTask));

		assertThrows(IllegalArgumentException.class,
				() -> taskService.updateTask(1L, newTask));

		verify(taskRepository, times(1)).findById(1L);
		verify(taskRepository, times(0)).save(any(Task.class));
	}

	@Test
	void givenTaskWithBlankDescription_whenUpdateTask_thenReturnIllegalArgumentException() {
		Task exsitingTask = new Task("Do groceries", "Go to teh shop and make groceries for today's dinner");
		exsitingTask.setId(1L);

		Task newTask = new Task("Make dinner", "");

		when(taskRepository.findById(1L)).thenReturn(Optional.of(exsitingTask));

		assertThrows(IllegalArgumentException.class,
				() -> taskService.updateTask(1L, newTask));

		verify(taskRepository, times(1)).findById(1L);
		verify(taskRepository, times(0)).save(any(Task.class));
	}

	@Test
	void givenValidId_whenDeleteTaskById_thenDeleteTask() {
		Task exsitingTask = new Task("Do groceries", "Go to teh shop and make groceries for today's dinner");
		exsitingTask.setId(1L);

		when(taskRepository.findById(1L)).thenReturn(Optional.of(exsitingTask));

		taskService.deleteTask(1L);

		verify(taskRepository, times(1)).findById(1L);
		verify(taskRepository, times(1)).delete(exsitingTask);
	}

	@Test
	void givenNonExistingId_whenDeleteProductById_thenThrowNoSuchElementException() {
		Long nonExistingId = 1000L;

		Task exsitingTask = new Task("Do groceries", "Go to teh shop and make groceries for today's dinner");
		exsitingTask.setId(1L);

		assertThrows(NoSuchElementException.class,
				() -> taskService.deleteTask(nonExistingId));

		verify(taskRepository, times(1)).findById(nonExistingId);
		verify(taskRepository, times(0)).delete(exsitingTask);
	}
}
