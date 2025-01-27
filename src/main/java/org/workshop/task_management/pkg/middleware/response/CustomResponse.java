package org.workshop.task_management.pkg.middleware.response;

public class CustomResponse {
    private String status;
    private String message;
    private Object data;

    public CustomResponse() {
    }

    public CustomResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static CustomResponse responseSuccess(String message, Object data) {
        return new CustomResponse("success", message, data);
    }

    public static CustomResponse responseError(String message) {
        return new CustomResponse("error", message, null);
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
