package com.norwayyachtbrockers.exception;

import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.norwayyachtbrockers.dto.response.AppEntityErrorResponse;
import com.norwayyachtbrockers.util.ErrorResponseUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

    @ExceptionHandler(AppEntityNotFoundException.class)
    public ResponseEntity<AppEntityErrorResponse> handleNotFoundException(AppEntityNotFoundException exc) {
        AppEntityErrorResponse error = ErrorResponseUtil.createErrorResponse(exc, HttpStatus.NOT_FOUND,
                List.of(exc.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppEntityErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            messages.add(fieldError.getDefaultMessage());
        }
        AppEntityErrorResponse error = ErrorResponseUtil.createErrorResponse(ex, HttpStatus.BAD_REQUEST, messages);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AppEntityErrorResponse> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            messages.add(constraintViolation.getMessage());
        }
        AppEntityErrorResponse error = ErrorResponseUtil.createErrorResponse(ex, HttpStatus.BAD_REQUEST, messages);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<AppEntityErrorResponse> handleMailSendException(MailSendException exc) {
        AppEntityErrorResponse error = ErrorResponseUtil.createErrorResponse(exc, HttpStatus.SERVICE_UNAVAILABLE,
                List.of("Error sending email: " + exc.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<AppEntityErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                               WebRequest request) {
        String message = "A record with the same key already exists. Please use a unique key.";
        AppEntityErrorResponse error = ErrorResponseUtil.createErrorResponse(ex, HttpStatus.CONFLICT, List.of(message));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<AppEntityErrorResponse> handleUsernameExistsException(UsernameExistsException ex) {
        AppEntityErrorResponse error = ErrorResponseUtil.createErrorResponse(ex, HttpStatus.BAD_REQUEST,
                List.of("An account with the given email already exists."));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AppEntityErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        AppEntityErrorResponse error = ErrorResponseUtil.createErrorResponse(ex, HttpStatus.BAD_REQUEST,
                List.of(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<AppEntityErrorResponse> handleNotAuthorizedException(NotAuthorizedException ex) {
        AppEntityErrorResponse error = ErrorResponseUtil.createErrorResponse(ex, HttpStatus.UNAUTHORIZED,
                List.of("Failed to authenticate: Incorrect username or password."));
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
