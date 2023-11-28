package com.norwayyachtbrockers.exception;

import com.norwayyachtbrockers.dto.response.AppEntityErrorResponse;
import com.norwayyachtbrockers.exception.AppEntityNotFoundException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AppEntityNotFoundException.class)
    public ResponseEntity<AppEntityErrorResponse> handleNotFoundException(AppEntityNotFoundException exc) {
        AppEntityErrorResponse error = new AppEntityErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setException(exc.getClass().getSimpleName());
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Handle general exceptions
    @ExceptionHandler
    public ResponseEntity<AppEntityErrorResponse> handleException(Exception exc) {

        AppEntityErrorResponse error = new AppEntityErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setException(exc.getClass().getSimpleName());
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
