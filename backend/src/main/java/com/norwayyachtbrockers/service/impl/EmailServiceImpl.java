package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.service.EmailService;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public boolean sendSimpleMessage(String userEmailAddress, String subject, String name, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(userEmailAddress); // User's email address as the sender

            // Predefined list of recipients
            String[] toAddresses = { "natali.kapii777@gmail.com",
                    "info@norseyacht.com"
            };
            message.setTo(toAddresses);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            return true;
        } catch (MailException exception) {
            System.err.println("Error sending email: " + exception.getMessage());
            return false;
        }
    }
}