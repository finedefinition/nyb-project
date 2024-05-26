package com.norwayyachtbrockers.exception;

import com.norwayyachtbrockers.dto.response.AppEntityErrorResponse;
import com.norwayyachtbrockers.util.ErrorResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppEntityErrorResponse> handleGlobalException(Exception exception) {
        AppEntityErrorResponse error = ErrorResponseUtil.createErrorResponse(exception,
                HttpStatus.INTERNAL_SERVER_ERROR, List.of(exception.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
