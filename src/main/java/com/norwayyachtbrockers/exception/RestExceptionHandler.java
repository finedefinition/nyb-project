package com.norwayyachtbrockers.exception;

import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.norwayyachtbrockers.dto.response.AppEntityErrorResponse;
import com.norwayyachtbrockers.dto.response.ExceptionMessageDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

//    @ExceptionHandler
//    public ResponseEntity<AppEntityErrorResponse> handleException(Exception exc) {
//
//        AppEntityErrorResponse error = new AppEntityErrorResponse();
//
//        error.setStatus(HttpStatus.BAD_REQUEST.value());
//        error.setMessage(exc.getMessage());
//        error.setException(exc.getClass().getSimpleName());
//        error.setTimeStamp(LocalDateTime.now());
//
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<ExceptionMessageDto> response = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            response.add(new ExceptionMessageDto(fieldError.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<ExceptionMessageDto> response = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            response.add(new ExceptionMessageDto(constraintViolation.getMessage()));
        }
        return ResponseEntity.badRequest().body(response);
    }

//    @ExceptionHandler(UsernameExistsException.class)
//    protected ResponseEntity<Object> handleUsernameExistsException(UsernameExistsException ex) {
//        ExceptionMessageDto response = new ExceptionMessageDto(ex.getErrorMessage());
//        return ResponseEntity.badRequest().body(response);
//    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<AppEntityErrorResponse> handleMailSendException(MailSendException exc) {
        AppEntityErrorResponse error = new AppEntityErrorResponse();

        error.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        error.setMessage("Error sending email: " + exc.getMessage());
        error.setException(exc.getClass().getSimpleName());
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        // This is a simplified way to extract a user-friendly message.
        // In a real scenario, you might want to parse the exception message or use a custom exception.
        String message = "A record with the same key already exists. Please use a unique key.";

        // Alternatively, you could extract and send back more detailed info from the exception itself
        // For example, by parsing the exception's message or using a custom error response class

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", message);
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameExistsException.class)
    protected ResponseEntity<Object> handleUsernameExistsException(UsernameExistsException ex) {
        ExceptionMessageDto response = new ExceptionMessageDto("An account with the given email already exists. Please use a different email or recover your password if you forgot it.");
        return ResponseEntity.badRequest().body(response);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private Map<String, Object> getConflictResponseBody(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT);
        body.put("message", ex.getMessage());
        return body;
    }
}
