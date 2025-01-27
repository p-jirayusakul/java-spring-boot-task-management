package org.workshop.task_management.pkg.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.workshop.task_management.pkg.middleware.response.CustomResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> handleGenericException(Exception ex, WebRequest request) {
        CustomResponse errorResponse = CustomResponse.responseError("An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        CustomResponse errorResponse = CustomResponse.responseError("Invalid argument: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponse> handleCustomException(CustomException ex, WebRequest request) {
        CustomResponse errorResponse = CustomResponse.responseError(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<CustomResponse> handleRepositoryException(RepositoryException ex, WebRequest request) {
        CustomResponse errorResponse = CustomResponse.responseError("Internal Server Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<CustomResponse> handleDomainException(DomainException ex, WebRequest request) {
        CustomResponse errorResponse = CustomResponse.responseError(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        CustomResponse errorResponse = CustomResponse.responseError(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<CustomResponse> handleSystemException(SystemException ex, WebRequest request) {
        CustomResponse errorResponse = CustomResponse.responseError("Internal Server Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnknowException.class)
    public ResponseEntity<CustomResponse> handleUnknowException(UnknowException ex, WebRequest request) {
        CustomResponse errorResponse = CustomResponse.responseError(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<CustomResponse> handleValidateException(ValidateException ex, WebRequest request) {
        CustomResponse errorResponse = CustomResponse.responseError(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}