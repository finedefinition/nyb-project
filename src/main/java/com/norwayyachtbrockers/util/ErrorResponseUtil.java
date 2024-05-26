package com.norwayyachtbrockers.util;

import com.norwayyachtbrockers.dto.response.AppEntityErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseUtil {

    public static AppEntityErrorResponse createErrorResponse(Exception exc, HttpStatus status, List<String> messages) {
        AppEntityErrorResponse error = new AppEntityErrorResponse();
        error.setStatus(status.value());
        error.setMessage(messages);
        error.setException(exc.getClass().getSimpleName());
        error.setTimeStamp(LocalDateTime.now());
        return error;
    }
}
