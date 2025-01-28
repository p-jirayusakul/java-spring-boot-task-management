package org.workshop.task_management.internal.server.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateTaskStatusRequest {
    @Min(message = "Task Status ID is required", value = 1)
    @NotNull(message = "Task Status ID is required")
    private Long taskStatusId;
    public UpdateTaskStatusRequest(Long taskStatusId) {
        this.taskStatusId = taskStatusId;
    }
    public Long getTaskStatusId() {
        return taskStatusId;
    }
    public void setTaskStatusId(Long taskStatusId) {
        this.taskStatusId = taskStatusId;
    }
}
