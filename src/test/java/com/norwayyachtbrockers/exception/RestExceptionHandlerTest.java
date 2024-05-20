package com.norwayyachtbrockers.exception;

import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.norwayyachtbrockers.dto.response.AppEntityErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@Order(270)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RestExceptionHandlerTest {

    @Mock
    private BindingResult bindingResult;

    @Mock
    private ConstraintViolation<String> constraintViolation;

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    private static final String EXCEPTION_MESSAGE = "Exception message";
    private static final String EMAIL_ERROR_MESSAGE = "Error sending email: " + EXCEPTION_MESSAGE;
    private static final String DATA_INTEGRITY_MESSAGE = "A record with the same key already exists. Please use a unique key.";
    private static final String USERNAME_EXISTS_MESSAGE = "An account with the given email already exists. Please use a different email or recover your password if you forgot it.";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restExceptionHandler = new RestExceptionHandler();
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(bindingResult, constraintViolation);
    }

    @Test
    @Order(10)
    @DisplayName("handleNotFoundException - Handles AppEntityNotFoundException")
    void testHandleNotFoundException() {
        // Arrange
        AppEntityNotFoundException ex = new AppEntityNotFoundException(EXCEPTION_MESSAGE);

        // Act
        ResponseEntity<AppEntityErrorResponse> response = restExceptionHandler.handleNotFoundException(ex);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "HTTP status should be 404 NOT FOUND");
        assertEquals(EXCEPTION_MESSAGE, response.getBody().getMessage().get(0), "Exception message should match");
    }

    @Test
    @Order(20)
    @DisplayName("handleValidationExceptions - Handles MethodArgumentNotValidException")
    void testHandleValidationExceptions() {
        // Arrange
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        List<FieldError> fieldErrors = List.of(new FieldError("objectName", "field", EXCEPTION_MESSAGE));
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        // Act
        ResponseEntity<AppEntityErrorResponse> response = restExceptionHandler.handleValidationExceptions(ex);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "HTTP status should be 400 BAD REQUEST");
        assertEquals(EXCEPTION_MESSAGE, response.getBody().getMessage().get(0), "Validation error message should match");
    }

    @Test
    @Order(30)
    @DisplayName("handleConstraintViolationExceptions - Handles ConstraintViolationException")
    void testHandleConstraintViolationExceptions() {
        // Arrange
        when(constraintViolation.getMessage()).thenReturn(EXCEPTION_MESSAGE);
        Set<ConstraintViolation<?>> constraintViolations = new HashSet<>(Collections.singletonList(constraintViolation));
        ConstraintViolationException ex = new ConstraintViolationException(constraintViolations);

        // Act
        ResponseEntity<AppEntityErrorResponse> response = restExceptionHandler.handleConstraintViolationExceptions(ex);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "HTTP status should be 400 BAD REQUEST");
        assertEquals(EXCEPTION_MESSAGE, response.getBody().getMessage().get(0), "Constraint violation message should match");
    }

    @Test
    @Order(40)
    @DisplayName("handleMailSendException - Handles MailSendException")
    void testHandleMailSendException() {
        // Arrange
        MailSendException ex = new MailSendException(EXCEPTION_MESSAGE);

        // Act
        ResponseEntity<AppEntityErrorResponse> response = restExceptionHandler.handleMailSendException(ex);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode(), "HTTP status should be 503 SERVICE UNAVAILABLE");
        assertEquals(EMAIL_ERROR_MESSAGE, response.getBody().getMessage().get(0), "Email error message should match");
    }

    @Test
    @Order(50)
    @DisplayName("handleDataIntegrityViolation - Handles DataIntegrityViolationException")
    void testHandleDataIntegrityViolation() {
        // Arrange
        DataIntegrityViolationException ex = new DataIntegrityViolationException(EXCEPTION_MESSAGE);

        // Act
        ResponseEntity<AppEntityErrorResponse> response = restExceptionHandler.handleDataIntegrityViolation(ex, null);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode(), "HTTP status should be 409 CONFLICT");
        assertEquals(DATA_INTEGRITY_MESSAGE, response.getBody().getMessage().get(0), "Data integrity violation message should match");
    }

    @Test
    @Order(60)
    @DisplayName("handleUsernameExistsException - Handles UsernameExistsException")
    void testHandleUsernameExistsException() {
        // Arrange
        UsernameExistsException ex = new UsernameExistsException(EXCEPTION_MESSAGE);

        // Act
        ResponseEntity<AppEntityErrorResponse> response = restExceptionHandler.handleUsernameExistsException(ex);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "HTTP status should be 400 BAD REQUEST");
        assertEquals(USERNAME_EXISTS_MESSAGE, response.getBody().getMessage().get(0), "Username exists message should match");
    }

    @Test
    @Order(70)
    @DisplayName("handleIllegalArgumentException - Handles IllegalArgumentException")
    void testHandleIllegalArgumentException() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException(EXCEPTION_MESSAGE);

        // Act
        ResponseEntity<AppEntityErrorResponse> response = restExceptionHandler.handleIllegalArgumentException(ex);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "HTTP status should be 400 BAD REQUEST");
        assertEquals(EXCEPTION_MESSAGE, response.getBody().getMessage().get(0), "Illegal argument message should match");
    }
}