package com.norwayyachtbrockers.dto.response;

import java.time.LocalDateTime;

public class AppEntityErrorResponse {
    private int status;
    private String exception;
    private String message;
    private LocalDateTime timeStamp;

    public AppEntityErrorResponse() {
    }

    public AppEntityErrorResponse(int status, String exception, String message, LocalDateTime timeStamp) {
        this.status = status;
        this.exception = exception;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

}
