package com.phatthakarn.task.domain.repository;

import com.phatthakarn.task.domain.model.Task;
import com.phatthakarn.task.domain.model.UpdateTask;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();
    Long save(Task task);
    void update(Long id, UpdateTask task);
    int delete(Long id);
}
