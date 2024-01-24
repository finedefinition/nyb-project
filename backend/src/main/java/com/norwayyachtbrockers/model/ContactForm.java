package com.norwayyachtbrockers.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactForm {

    @Email(message = "Invalid email format")
    private String userEmail;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 30, message = "Name cannot exceed 30 characters")
    private String name;

    @NotBlank(message = "Message cannot be empty")
    @Size(max = 5000, message = "Message cannot exceed 5000 characters")
    private String message;
}
