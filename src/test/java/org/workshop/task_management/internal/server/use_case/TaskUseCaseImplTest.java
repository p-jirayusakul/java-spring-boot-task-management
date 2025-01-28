package org.workshop.task_management.internal.server.use_case;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.workshop.task_management.internal.server.domain.entities.task.Task;
import org.workshop.task_management.internal.server.domain.entities.task.TaskID;
import org.workshop.task_management.internal.server.domain.repositories.TaskRepository;
import org.workshop.task_management.pkg.exceptions.RepositoryException;
import org.workshop.task_management.pkg.exceptions.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskUseCaseImplTest {

    @MockitoBean
    private TaskRepository taskRepository;

    @Autowired
    private TaskUseCaseImpl taskUseCase;

    @Test
    void listTasks_shouldReturnAllTasks() {
        // Arrange
        Task task1 = new Task(1L, "Task 1", "Description 1", 1L, 1L, 1L, null, null, null);
        Task task2 = new Task(2L, "Task 2", "Description 2", 2L, 2L, 2L, null, null, null);
        when(taskRepository.listTasks()).thenReturn(Arrays.asList(task1, task2));

        // Act
        List<Task> tasks = taskUseCase.listTasks();

        // Assert
        assertThat(tasks).isNotNull();
        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Task 1");
        assertThat(tasks.get(1).getTitle()).isEqualTo("Task 2");
    }

    @Test
    void listTasks_shouldReturnEmptyListWhenNoTasksAvailable() {
        // Arrange
        when(taskRepository.listTasks()).thenReturn(List.of());

        // Act
        List<Task> tasks = taskUseCase.listTasks();

        // Assert
        assertThat(tasks).isNotNull();
        assertThat(tasks).isEmpty();
    }

    @Test
    void listTasks_shouldHandleRepositoryException() {
        when(taskRepository.listTasks()).thenThrow(new RepositoryException("Internal Server Error"));
        RepositoryException exception = org.junit.jupiter.api.Assertions.assertThrows(RepositoryException.class,
                () -> taskUseCase.listTasks());

        assertThat(exception.getMessage()).isEqualTo("Internal Server Error");
    }

    @Test
    void createTask_shouldReturnTaskIDWhenTaskIsCreatedSuccessfully() {
        // Arrange
        Task task = new Task(null, "New Task", "Task Description", 1L, 1L, 1844995683120058368L, null, null, null);
        when(taskRepository.createTask(task)).thenReturn(123L);

        // Act
        TaskID taskId = taskUseCase.createTask(task);

        // Assert
        assertThat(taskId).isNotNull();
        assertThat(taskId.getId()).isEqualTo(123L);
    }

    @Test
    void createTask_shouldHandleRepositoryException() {
        // Arrange
        Task task = new Task(null, "New Task", "Task Description", 1L, 1L, 1844995683120058368L, null, null, null);
        when(taskRepository.createTask(task)).thenThrow(new RepositoryException("Internal Server Error"));

        // Act
        RepositoryException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RepositoryException.class,
                () -> taskUseCase.createTask(task)
        );

        // Assert
        assertThat(exception.getMessage()).isEqualTo("Internal Server Error");
    }

    @Test
    void updateTask_shouldUpdateTaskSuccessfully() {
        // Arrange
        Task task = new Task(1L, "Updated Task", "Updated Description", 1L, 1L, 1L, null, null, null);
        when(taskRepository.taskExists(task.getId())).thenReturn(true);

        // Act
        taskUseCase.updateTask(task);

        // Assert
        assertThat(task.getTitle()).isEqualTo("Updated Task");
        assertThat(task.getDescription()).isEqualTo("Updated Description");
    }

    @Test
    void updateTask_shouldThrowNotFoundExceptionWhenTaskDoesNotExist() {
        // Arrange
        Task task = new Task(999L, "Non-existent Task", "Non-existent Description", 1L, 1L, 1L, null, null, null);
        when(taskRepository.taskExists(task.getId())).thenReturn(false);

        // Act
        NotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                NotFoundException.class,
                () -> taskUseCase.updateTask(task)
        );

        // Assert
        assertThat(exception.getMessage()).isEqualTo("Task with ID 999 not found.");
    }
}