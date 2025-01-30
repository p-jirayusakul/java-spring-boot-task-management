package org.workshop.task_management.internal.server.domain.entities.users;

public class Users {
    private Long id;
    private String password;

    public Users() {
    }

    public Users(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
