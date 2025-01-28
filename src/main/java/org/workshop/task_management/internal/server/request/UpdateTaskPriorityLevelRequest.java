package org.workshop.task_management.internal.server.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateTaskPriorityLevelRequest {
    @Min(message = "Priority Level ID is required", value = 1)
    @NotNull(message = "Priority Level ID is required")
    private Long priorityLevelsId;

    public UpdateTaskPriorityLevelRequest(Long priorityLevelsId) {
        this.priorityLevelsId = priorityLevelsId;
    }
    public Long getPriorityLevelsId() {
        return priorityLevelsId;
    }
    public void setPriorityLevelsId(Long priorityLevelsId) {
        this.priorityLevelsId = priorityLevelsId;
    }
}
