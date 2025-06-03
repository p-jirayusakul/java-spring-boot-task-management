package com.phatthakarn.task.application;

import com.phatthakarn.task.domain.model.Task;
import com.phatthakarn.task.domain.model.UpdateTask;

import java.util.List;

public interface TaskUseCase {
    Long createTask(String title, String description);
    List<Task> getTasks();
    void updateTask(Long id, UpdateTask task);
    boolean deleteTask(Long id);
}
