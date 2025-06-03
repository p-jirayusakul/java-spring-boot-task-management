package com.phatthakarn.task.application;

import com.phatthakarn.task.domain.model.Task;
import com.phatthakarn.task.domain.model.UpdateTask;
import com.phatthakarn.task.domain.repository.TaskRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class TaskUseCaseImpl implements TaskUseCase {

    private final TaskRepository repository;

    public TaskUseCaseImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long createTask(String title, String description) {
        Task task = new Task(title, description);
        return this.repository.save(task);
    }

    @Override
    public List<Task> getTasks() {
        return this.repository.findAll();
    }

    @Override
    public void updateTask(Long id, UpdateTask task) {
        this.repository.update(id, task);
    }

    @Override
    public boolean deleteTask(Long id) {
        return this.repository.delete(id) > 0;
    }

}

