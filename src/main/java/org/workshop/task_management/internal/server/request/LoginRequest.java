package org.workshop.task_management.internal.server.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class LoginRequest {
    @NotEmpty(message = "username is required")
    @NotNull(message = "username is required")
    private String username;

    @NotEmpty(message = "username is required")
    @NotNull(message = "password is required")
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
