package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.ContactFormRequestDto;
import com.norwayyachtbrockers.model.ContactForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactFormMapper {
    public ContactForm createContactFormFromDto(ContactFormRequestDto dto) {
        ContactForm contactForm = new ContactForm();
        contactForm.setUserEmail(dto.getUserEmail());
        contactForm.setName(dto.getName());
        contactForm.setMessage(dto.getMessage());

        return contactForm;
    }
}
