package org.workshop.task_management.pkg.exceptions;

public class BusinessException  extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
