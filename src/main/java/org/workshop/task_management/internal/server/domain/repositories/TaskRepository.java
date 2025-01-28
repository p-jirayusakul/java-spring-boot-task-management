package org.workshop.task_management.internal.server.domain.repositories;

import org.workshop.task_management.internal.server.domain.entities.task.Task;
import org.workshop.task_management.internal.server.domain.entities.task.TaskPriorityLevel;
import org.workshop.task_management.internal.server.domain.entities.task.TaskStatus;

import java.util.List;

public interface TaskRepository {
    List<Task> listTasks();
    Task getTask(Long id);
    boolean taskExists(Long id);
    Long createTask(Task task);
    void updateTask(Task task);
    void updateTaskStatus(TaskStatus task);
    void updateTaskPriorityLevel(TaskPriorityLevel task);
    void deleteTask(Long id);
}
