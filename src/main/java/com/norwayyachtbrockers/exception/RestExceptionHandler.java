package com.norwayyachtbrockers.exception;

import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.norwayyachtbrockers.dto.response.AppEntityErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)

    public ResponseEntity<AppEntityErrorResponse> handleGlobalException(Exception exception) {
        AppEntityErrorResponse error = createErrorResponse(exception,
                HttpStatus.NOT_FOUND, List.of(exception.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppEntityNotFoundException.class)
    public ResponseEntity<AppEntityErrorResponse> handleNotFoundException(AppEntityNotFoundException exc) {
        AppEntityErrorResponse error = createErrorResponse(exc, HttpStatus.NOT_FOUND, List.of(exc.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppEntityErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            messages.add(fieldError.getDefaultMessage());
        }
        AppEntityErrorResponse error = createErrorResponse(ex, HttpStatus.BAD_REQUEST, messages);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AppEntityErrorResponse> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            messages.add(constraintViolation.getMessage());
        }
        AppEntityErrorResponse error = createErrorResponse(ex, HttpStatus.BAD_REQUEST, messages);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<AppEntityErrorResponse> handleMailSendException(MailSendException exc) {
        AppEntityErrorResponse error = createErrorResponse(exc, HttpStatus.SERVICE_UNAVAILABLE, List.of("Error sending email: " + exc.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<AppEntityErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        String message = "A record with the same key already exists. Please use a unique key.";
        AppEntityErrorResponse error = createErrorResponse(ex, HttpStatus.CONFLICT, List.of(message));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameExistsException.class)
    protected ResponseEntity<AppEntityErrorResponse> handleUsernameExistsException(UsernameExistsException ex) {
        AppEntityErrorResponse error = createErrorResponse(ex, HttpStatus.BAD_REQUEST, List.of("An account with the given email already exists. Please use a different email or recover your password if you forgot it."));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AppEntityErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        AppEntityErrorResponse error = createErrorResponse(ex, HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private AppEntityErrorResponse createErrorResponse(Exception exc, HttpStatus status, List<String> messages) {
        AppEntityErrorResponse error = new AppEntityErrorResponse();
        error.setStatus(status.value());
        error.setMessage(messages);
        error.setException(exc.getClass().getSimpleName());
        error.setTimeStamp(LocalDateTime.now());
        return error;
    }
}
