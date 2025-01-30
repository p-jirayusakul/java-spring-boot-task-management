package org.workshop.task_management.pkg.exceptions;

public class UnAuthorization extends RuntimeException {
    public UnAuthorization(String message) {
        super(message);
    }
}
