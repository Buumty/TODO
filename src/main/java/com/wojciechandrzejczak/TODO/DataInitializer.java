package com.wojciechandrzejczak.TODO;

import com.wojciechandrzejczak.TODO.task.Task;
import com.wojciechandrzejczak.TODO.task.TaskRepository;
import com.wojciechandrzejczak.TODO.task.TaskStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(TaskRepository taskRepository) {
        return args -> {
            if (taskRepository.count() == 0) {
                Task t1 = new Task(
                        "Do groceries",
                        "Buy groceries for the next few days."
                );
                t1.setTaskStatus(TaskStatus.NEW);

                Task t2 = new Task(
                        "Make dinner",
                        "Cook a quick and healthy dinner."
                );
                t2.setTaskStatus(TaskStatus.IN_PROGRESS);

                Task t3 = new Task(
                        "Learn English",
                        "Practice English for at least 30 minutes."
                );
                t3.setTaskStatus(TaskStatus.DONE);

                taskRepository.save(t1);
                taskRepository.save(t2);
                taskRepository.save(t3);
            }
        };
    }
}