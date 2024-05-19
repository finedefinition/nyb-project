package com.norwayyachtbrockers.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AppEntityErrorResponse {
    private int status;
    private String exception;
    private List<String> message;
    private LocalDateTime timeStamp;
}
