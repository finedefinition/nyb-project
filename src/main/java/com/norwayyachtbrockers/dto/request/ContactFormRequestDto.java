package com.norwayyachtbrockers.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactFormRequestDto {
    @NotNull(message = "Email is required.")
    @NotBlank(message = "User email can not be empty.")
    @Email(message = "Please provide a valid email format.")
    private String userEmail;

    @NotNull(message = "Name is required.")
    @NotBlank(message = "Name can not be empty.")
    @Size(max = 30, message = "Name must not exceed 30 characters.")
    private String name;

    @NotNull(message = "Message is required.")
    @NotBlank(message = "Message can not be empty.")
    @Size(max = 5000, message = "Message cannot exceed 5000 characters")
    private String message;
}
