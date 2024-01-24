package com.norwayyachtbrockers.service;

public interface EmailService {
    boolean sendSimpleMessage(String to, String subject, String name, String text);
}
