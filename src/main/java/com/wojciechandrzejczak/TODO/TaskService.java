package com.wojciechandrzejczak.TODO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
