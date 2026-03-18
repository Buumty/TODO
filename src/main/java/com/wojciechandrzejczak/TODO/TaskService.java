package com.wojciechandrzejczak.TODO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        if (task == null) throw new IllegalArgumentException("Task cannot be null");
        if (task.getTitle().isBlank()) throw new IllegalArgumentException("Task title cannot be blank");

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task newProduct) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Task with given ID does not exist"));

        if (!newProduct.getTitle().isBlank()) {
            existingTask.setTitle(newProduct.getTitle());
        } else throw new IllegalArgumentException("Task title cannot be blank");
        if (!newProduct.getDescription().isBlank()) {
            existingTask.setDescription(newProduct.getDescription());
        } else throw new IllegalArgumentException("Task description cannot be blank");

        return taskRepository.save(newProduct);
    }

    public void deleteTask(Long id) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Task with given ID does not exist"));

        taskRepository.delete(existingTask);
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Task with given ID does not exist"));
    }
}
