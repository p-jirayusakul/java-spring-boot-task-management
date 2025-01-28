package org.workshop.task_management.internal.server.domain.entities.task;

public class TaskStatus {
    private Long id;
    private Long taskStatusId;
    private Long updatedBy;

    public TaskStatus() {
    }

    public TaskStatus(Long id, Long taskStatusId) {
        this.id = id;
        this.taskStatusId = taskStatusId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskStatusId() {
        return taskStatusId;
    }
    public void setTaskStatusId(Long taskStatusId) {
        this.taskStatusId = taskStatusId;
    }
    public Long getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}
