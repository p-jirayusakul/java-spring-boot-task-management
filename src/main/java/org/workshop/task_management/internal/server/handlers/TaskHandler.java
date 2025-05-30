package org.workshop.task_management.internal.server.handlers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.workshop.task_management.internal.server.domain.entities.task.Task;
import org.workshop.task_management.internal.server.domain.entities.task.TaskID;
import org.workshop.task_management.internal.server.domain.entities.task.TaskPriorityLevel;
import org.workshop.task_management.internal.server.domain.entities.task.TaskStatus;
import org.workshop.task_management.internal.server.domain.use_case.TaskUseCase;
import org.workshop.task_management.internal.server.request.TaskRequest;
import org.workshop.task_management.internal.server.request.UpdateTaskPriorityLevelRequest;
import org.workshop.task_management.internal.server.request.UpdateTaskStatusRequest;
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
        return createSuccessResponse("get list task completed", items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getTask(@PathVariable("id") Long id) {
        Task items = taskUseCase.getTask(id);
        return createSuccessResponse("get task completed", items);
    }

    @PostMapping("")
    public ResponseEntity<CustomResponse> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        Long userID = getCurrentUserId();
        Task task = mapTaskFromRequest(taskRequest);
        task.setCreatedBy(userID);

        TaskID id = taskUseCase.createTask(task);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(CustomResponse.responseSuccess("create task completed", id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateTask(@PathVariable("id") Long id, @Valid @RequestBody TaskRequest taskRequest) {
        Long userID = getCurrentUserId();
        Task task = mapTaskFromRequest(taskRequest);
        task.setId(id);
        task.setUpdatedBy(userID);

        taskUseCase.updateTask(task);
        return createSuccessResponse("update task completed", null);
    }

    @PatchMapping("/{id}/task-status")
    public ResponseEntity<CustomResponse> updateTaskStatus(@PathVariable("id") Long id, @Valid @RequestBody UpdateTaskStatusRequest taskRequest) {
        Long userID = getCurrentUserId();

        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setId(id);
        taskStatus.setTaskStatusId(taskRequest.getTaskStatusId());
        taskStatus.setUpdatedBy(userID);

        taskUseCase.updateTaskStatus(taskStatus);
        return createSuccessResponse("update task status completed", null);
    }

    @PatchMapping("/{id}/priority-level")
    public ResponseEntity<CustomResponse> updateTaskPriorityLevel(@PathVariable("id") Long id, @Valid @RequestBody UpdateTaskPriorityLevelRequest taskRequest) {
        Long userID = getCurrentUserId();

        TaskPriorityLevel priorityLevel = new TaskPriorityLevel();
        priorityLevel.setId(id);
        priorityLevel.setPriorityLevelId(taskRequest.getPriorityLevelsId());
        priorityLevel.setUpdatedBy(userID);

        taskUseCase.updateTaskPriorityLevel(priorityLevel);
        return createSuccessResponse("update task priority level completed", null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteTask(@PathVariable("id") Long id) {
        taskUseCase.deleteTask(id);
        return createSuccessResponse("delete task completed", null);
    }

    private Task mapTaskFromRequest(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriorityLevelsId(taskRequest.getPriorityLevelsId());
        task.setTaskStatusId(taskRequest.getTaskStatusId());
        return task;
    }

    private ResponseEntity<CustomResponse> createSuccessResponse(String message, Object data) {
        CustomResponse response = CustomResponse.responseSuccess(message, data);
        return ResponseEntity.ok(response);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                try {
                    return Long.parseLong((String) principal);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }



}
