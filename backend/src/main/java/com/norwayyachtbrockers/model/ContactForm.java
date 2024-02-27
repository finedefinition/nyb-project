package com.norwayyachtbrockers.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactForm {

    private String userEmail;

    private String name;

    private String message;
}
