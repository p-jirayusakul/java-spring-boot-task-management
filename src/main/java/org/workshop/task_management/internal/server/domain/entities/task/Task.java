package org.workshop.task_management.internal.server.domain.entities.task;

import java.time.LocalDateTime;

public class Task {
    private Long id;
    private String title;
    private String description;
    private Long taskStatusId;
    private Long priorityLevelsId;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    public Task() {
    }

    public Task(Long id, String title, String description, Long taskStatusId, Long priorityLevelsId, Long createdBy, LocalDateTime createdAt, LocalDateTime updatedAt, Long updatedBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskStatusId = taskStatusId;
        this.priorityLevelsId = priorityLevelsId;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}
