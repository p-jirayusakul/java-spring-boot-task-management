package org.workshop.task_management.internal.server.handlers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.workshop.task_management.internal.server.domain.entities.task.Task;
import org.workshop.task_management.internal.server.domain.entities.task.TaskID;
import org.workshop.task_management.internal.server.domain.use_case.TaskUseCase;
import org.workshop.task_management.internal.server.request.TaskRequest;
import org.workshop.task_management.pkg.middleware.response.CustomResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskHandler {
    private final TaskUseCase taskUseCase;

    public TaskHandler(TaskUseCase taskUseCase) {
        this.taskUseCase = taskUseCase;
    }

    @GetMapping("")
    public ResponseEntity<CustomResponse> listTasks() {
        List<Task> items = taskUseCase.listTasks();
        CustomResponse response = CustomResponse.responseSuccess("get list task completed", items);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getTask(@PathVariable("id") Long id) {
        Task items = taskUseCase.getTask(id);
        CustomResponse response = CustomResponse.responseSuccess("get task completed", items);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<CustomResponse> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriorityLevelsId(taskRequest.getPriorityLevelsId());
        task.setTaskStatusId(taskRequest.getTaskStatusId());

        Long createdBy = 1844995683120058368L;
        task.setCreatedBy(createdBy);

        TaskID id = taskUseCase.createTask(task);
        if (id == null) {
            CustomResponse errorResponse = CustomResponse.responseError("Failed to create task");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        CustomResponse response = CustomResponse.responseSuccess("create task completed", id);
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateTask(@PathVariable("id") Long id, @Valid @RequestBody TaskRequest task) {
        try {
            Task item = new Task();
            item.setTitle(task.getTitle());
            item.setDescription(task.getDescription());
            item.setPriorityLevelsId(task.getPriorityLevelsId());
            item.setTaskStatusId(task.getTaskStatusId());
            item.setId(id);

            Long updatedBy = 1844995683120058368L;
            item.setUpdatedBy(updatedBy);

            taskUseCase.updateTask(item);
            CustomResponse response = CustomResponse.responseSuccess("update task completed", null);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            CustomResponse errorResponse = CustomResponse.responseError("Invalid input: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            CustomResponse errorResponse = CustomResponse.responseError("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteTask(@PathVariable("id") Long id) {
        taskUseCase.deleteTask(id);
        CustomResponse response = CustomResponse.responseSuccess("delete task completed", null);
        return ResponseEntity.ok(response);
    }

}
