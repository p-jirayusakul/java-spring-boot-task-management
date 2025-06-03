package com.phatthakarn.task.adapter.request;


import jakarta.validation.constraints.NotBlank;

public class TaskDTO {

    @NotBlank
    private String title;

    private String description;

    public TaskDTO() {}

    public TaskDTO(String title, String description) {
        this.title = title;
        this.description = description;
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
}
