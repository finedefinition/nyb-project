package com.norwayyachtbrockers.service.impl;

import com.norwayyachtbrockers.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private String[] toAddresses = {
            // "natali.kapii777@gmail.com",
            // "antek1809@gmail.com",
            "info@norseyacht.com"
    };

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    @Transactional
    public boolean sendSimpleMessage(String userEmailAddress, String subject, String name, String text) {
        if (toAddresses == null || toAddresses.length == 0) {
            System.err.println("Error: toAddresses is null or empty.");
            return false;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(userEmailAddress);
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