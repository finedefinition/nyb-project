package com.norwayyachtbrockers.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Order(500)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender emailSender;

    private static final String USER_EMAIL = "user@example.com";
    private static final String SUBJECT = "Test Subject";
    private static final String NAME = "Test Name";
    private static final String TEXT = "Test Email Body";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(10)
    @DisplayName("sendSimpleMessage - toAddresses is null")
    void testSendSimpleMessage_ToAddressesIsNull() throws Exception {
        // Arrange
        Field toAddressesField = EmailServiceImpl.class.getDeclaredField("toAddresses");
        toAddressesField.setAccessible(true);
        toAddressesField.set(emailService, null);

        // Act
        boolean result = emailService.sendSimpleMessage(USER_EMAIL, SUBJECT, NAME, TEXT);

        // Assert
        assertFalse(result, "Email send should fail due to null toAddresses");
        verify(emailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    @Order(20)
    @DisplayName("sendSimpleMessage - Successful email send")
    void testSendSimpleMessage_Success() {
        // Arrange
        doNothing().when(emailSender).send(any(SimpleMailMessage.class));

        // Act
        boolean result = emailService.sendSimpleMessage(USER_EMAIL, SUBJECT, NAME, TEXT);

        // Assert
        assertTrue(result, "Email should be sent successfully");
        verify(emailSender, times(1));
    }
}