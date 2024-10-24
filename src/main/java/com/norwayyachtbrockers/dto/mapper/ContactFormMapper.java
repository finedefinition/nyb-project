package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.ContactFormRequestDto;
import com.norwayyachtbrockers.model.ContactForm;
import jakarta.validation.Valid;

public class ContactFormMapper {

    public static ContactForm createContactFormFromDto(@Valid ContactFormRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Failed to create ContactForm: DTO cannot be null");
        }
        ContactForm contactForm = new ContactForm();
        contactForm.setUserEmail(dto.getUserEmail());
        contactForm.setName(dto.getName().trim());
        contactForm.setMessage(dto.getMessage());
        return contactForm;
    }
}
