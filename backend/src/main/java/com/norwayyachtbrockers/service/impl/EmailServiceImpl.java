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

    public boolean sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("yourEmail@gmail.com");
            message.setTo(to);
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