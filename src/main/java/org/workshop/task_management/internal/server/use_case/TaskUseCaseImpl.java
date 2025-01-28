package org.workshop.task_management.internal.server.use_case;

import org.springframework.stereotype.Service;
import org.workshop.task_management.internal.server.domain.entities.task.Task;
import org.workshop.task_management.internal.server.domain.entities.task.TaskID;
import org.workshop.task_management.internal.server.domain.entities.task.TaskPriorityLevel;
import org.workshop.task_management.internal.server.domain.entities.task.TaskStatus;
import org.workshop.task_management.internal.server.domain.repositories.TaskRepository;
import org.workshop.task_management.internal.server.domain.use_case.TaskUseCase;
import org.workshop.task_management.pkg.exceptions.NotFoundException;

import java.util.List;

@Service
public class TaskUseCaseImpl implements TaskUseCase {
    private final TaskRepository taskRepository;

    public TaskUseCaseImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> listTasks() {
        return taskRepository.listTasks();
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.getTask(id);
    }

    @Override
    public TaskID createTask(Task task) {
        Long id = taskRepository.createTask(task);
        return new TaskID(id);
    }

    @Override
    public void updateTask(Task task) {
        validateTaskExists(task.getId());
        taskRepository.updateTask(task);
    }

    @Override
    public void updateTaskStatus(TaskStatus taskStatus) {
        validateTaskExists(taskStatus.getId());
        taskRepository.updateTaskStatus(taskStatus);
    }

    @Override
    public void updateTaskPriorityLevel(TaskPriorityLevel priorityLevel) {
        validateTaskExists(priorityLevel.getId());
        taskRepository.updateTaskPriorityLevel(priorityLevel);
    }

    @Override
    public void deleteTask(Long id) {
        validateTaskExists(id);
        taskRepository.deleteTask(id);
    }

    private void validateTaskExists(Long id) {
        if (!taskRepository.taskExists(id)) {
            throw new NotFoundException(formatTaskNotFoundException(id));
        }
    }

    private String formatTaskNotFoundException(Long id) {
        return "Task with ID " + id + " not found.";
    }
}
