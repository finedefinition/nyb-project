package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.ContactFormRequestDto;
import com.norwayyachtbrockers.model.ContactForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@RequiredArgsConstructor
public class ContactFormMapper {

    public ContactForm createContactFormFromDto(@Valid ContactFormRequestDto dto) {
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
