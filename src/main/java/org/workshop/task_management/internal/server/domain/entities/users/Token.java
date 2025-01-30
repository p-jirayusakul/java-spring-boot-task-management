package org.workshop.task_management.internal.server.domain.entities.users;

public class Token {
    private String token;

    public Token(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
