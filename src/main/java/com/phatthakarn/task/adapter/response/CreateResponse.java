package com.phatthakarn.task.adapter.response;

public class CreateResponse {
    private Long id;

    public CreateResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
