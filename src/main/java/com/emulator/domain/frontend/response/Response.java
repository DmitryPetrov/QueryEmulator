package com.emulator.domain.frontend.response;

public class Response {

    private String status = "";
    private String message = "";

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

    public String getLogInfo() {
        return "ResponseBodyData{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
