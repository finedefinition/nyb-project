package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.mapper.ContactFormMapper;
import com.norwayyachtbrockers.dto.request.ContactFormRequestDto;
import com.norwayyachtbrockers.model.ContactForm;
import com.norwayyachtbrockers.service.EmailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Order(30)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ContactControllerTest {

    @MockBean
    private EmailService emailService;

    @MockBean
    private ContactFormMapper contactFormMapper;

    @Autowired
    private ContactController contactController;

    private ContactFormRequestDto contactFormRequestDto;
    private ContactForm contactForm;

    private static final String USER_EMAIL = "user@example.com";
    private static final String CONTACT_NAME = "John Doe";
    private static final String CONTACT_MESSAGE = "Hello, this is a test message.";
    private static final String SUCCESS_MESSAGE = "Message sent successfully!";
    private static final String FAILURE_MESSAGE = "Failed to send message.";
    private static final String EMAIL_SUBJECT = "New contact message from: " + CONTACT_NAME;
    private static final String EMAIL_BODY = CONTACT_MESSAGE + "\n\n" + "From user: " + CONTACT_NAME;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contactFormRequestDto = new ContactFormRequestDto();
        contactFormRequestDto.setUserEmail(USER_EMAIL);
        contactFormRequestDto.setName(CONTACT_NAME);
        contactFormRequestDto.setMessage(CONTACT_MESSAGE);

        contactForm = new ContactForm();
        contactForm.setUserEmail(USER_EMAIL);
        contactForm.setName(CONTACT_NAME);
        contactForm.setMessage(CONTACT_MESSAGE);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(emailService, contactFormMapper);
    }

    @Test
    @Order(10)
    @DisplayName("sendContactMessage - Successfully sends a contact message")
    void testSendContactMessage_Success() {
        // Arrange
        when(contactFormMapper.createContactFormFromDto(contactFormRequestDto)).thenReturn(contactForm);
        when(emailService.sendSimpleMessage(USER_EMAIL, EMAIL_SUBJECT, CONTACT_NAME, EMAIL_BODY)).thenReturn(true);

        // Act
        ResponseEntity<String> response = contactController.sendContactMessage(contactFormRequestDto);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");
        assertEquals(SUCCESS_MESSAGE, response.getBody(), "Response body should match success message");

        verify(emailService, times(1))
                .sendSimpleMessage(USER_EMAIL, EMAIL_SUBJECT, CONTACT_NAME, EMAIL_BODY);
    }

    @Test
    @Order(20)
    @DisplayName("sendContactMessage - Fails to send a contact message")
    void testSendContactMessage_Failure() {
        // Arrange
        when(contactFormMapper.createContactFormFromDto(contactFormRequestDto)).thenReturn(contactForm);
        when(emailService
                .sendSimpleMessage(USER_EMAIL, EMAIL_SUBJECT, CONTACT_NAME, EMAIL_BODY)).thenReturn(false);

        // Act
        ResponseEntity<String> response = contactController.sendContactMessage(contactFormRequestDto);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()
                , "Status code should be 500 Internal Server Error");
        assertEquals(FAILURE_MESSAGE, response.getBody(), "Response body should match failure message");

        verify(emailService, times(1))
                .sendSimpleMessage(USER_EMAIL, EMAIL_SUBJECT, CONTACT_NAME, EMAIL_BODY);
    }
}