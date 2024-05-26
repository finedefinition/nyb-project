package com.norwayyachtbrockers.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactFormRequestDto {
    @NotBlank(message = "User email is required.")
    @Email(message = "Please provide a valid email format.")
    private String userEmail;

    @NotBlank(message="Name is required.")
    @Size(max = 30, message="Name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-'.,0-9]*$", message = "Name must start with a capital letter and can include" +
            " letters, spaces, hyphens, apostrophes, periods, and numerals.")
    private String name;

    @NotBlank(message = "Message is required.")
    @Size(max = 5000, message = "Message cannot exceed 5000 characters.")
    private String message;
}
