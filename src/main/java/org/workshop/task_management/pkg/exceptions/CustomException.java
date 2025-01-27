package org.workshop.task_management.pkg.exceptions;

public class CustomException extends RuntimeException {
    private final int errorCode;

    public CustomException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}