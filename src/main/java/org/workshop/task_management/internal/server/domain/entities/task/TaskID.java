package org.workshop.task_management.internal.server.domain.entities.task;

public class TaskID {
    private Long id;

    public TaskID() {
    }
    public TaskID(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
