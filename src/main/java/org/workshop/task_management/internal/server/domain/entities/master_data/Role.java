package org.workshop.task_management.internal.server.domain.entities.master_data;

public class Role {
    private Long id;
    private String title;
    private String code;

    public Role() {
    }

    public Role(Long id, String title, String code) {
        this.id = id;
        this.title = title;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
