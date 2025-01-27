package org.workshop.task_management.internal.server.request;

import jakarta.validation.constraints.*;

public class TaskRequest {
    @NotBlank(message = "Title is required")
    @NotNull(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Min(message = "Task Status ID is required", value = 1)
    @NotNull(message = "Task Status ID is required")
    private Long taskStatusId;

    @Min(message = "Priority Level ID is required", value = 1)
    @NotNull(message = "Priority Level ID is required")
    private Long priorityLevelsId;

    public TaskRequest(String title, String description, Long taskStatusId, Long priorityLevelsId) {
        this.title = title;
        this.description = description;
        this.taskStatusId = taskStatusId;
        this.priorityLevelsId = priorityLevelsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTaskStatusId() {
        return taskStatusId;
    }

    public void setTaskStatusId(Long taskStatusId) {
        this.taskStatusId = taskStatusId;
    }

    public Long getPriorityLevelsId() {
        return priorityLevelsId;
    }

    public void setPriorityLevelsId(Long priorityLevelsId) {
        this.priorityLevelsId = priorityLevelsId;
    }
}
