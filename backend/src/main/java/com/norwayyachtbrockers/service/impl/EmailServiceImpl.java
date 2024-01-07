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

    public boolean sendSimpleMessage(String userEmailAddress, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(userEmailAddress); // User's email address as the sender

            // Predefined list of recipients
            String[] toAddresses = {"sergiibezrukov@gmail.com", "natali.kapii777@gmail.com"};
            message.setTo(toAddresses);

            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            return true;
        } catch (MailException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}