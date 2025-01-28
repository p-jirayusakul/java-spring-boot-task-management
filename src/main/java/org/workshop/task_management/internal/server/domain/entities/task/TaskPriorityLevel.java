package org.workshop.task_management.internal.server.domain.entities.task;

public class TaskPriorityLevel {
    private Long id;
    private Long priorityLevelId;
    private Long updatedBy;

    public TaskPriorityLevel() {
    }
    public TaskPriorityLevel(Long id, Long priorityLevelId) {
        this.id = id;
        this.priorityLevelId = priorityLevelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPriorityLevelId() {
        return priorityLevelId;
    }

    public void setPriorityLevelId(Long priorityLevelId) {
        this.priorityLevelId = priorityLevelId;
    }
    public Long getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}
