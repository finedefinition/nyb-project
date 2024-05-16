package com.norwayyachtbrockers.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppEntityErrorResponse {
    private int status;
    private String exception;
    private String message;
    private LocalDateTime timeStamp;
}
