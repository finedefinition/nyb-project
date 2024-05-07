package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.dto.mapper.ContactFormMapper;
import com.norwayyachtbrockers.dto.request.ContactFormRequestDto;
import com.norwayyachtbrockers.model.ContactForm;
import com.norwayyachtbrockers.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(
        name = "Contact form API",
        description = "Contact form service to send message using 3rd party Application MAILGUN"
)
@RestController
@RequestMapping("/contact")
public class ContactController {

    private final EmailService emailService;

    private final ContactFormMapper contactFormMapper;

    public ContactController(EmailService emailService, ContactFormMapper contactFormMapper) {
        this.emailService = emailService;
        this.contactFormMapper = contactFormMapper;
    }

    @PostMapping
    public ResponseEntity<String> sendContactMessage(@Valid @RequestBody ContactFormRequestDto dto) {

        ContactForm contactForm =  contactFormMapper.createContactFormFromDto(dto);
        boolean sent = emailService.sendSimpleMessage(
                contactForm.getUserEmail(),
                "New contact message from: " + contactForm.getName(),
                contactForm.getName(),
                contactForm.getMessage() + "\n\n" + "From user: " + contactForm.getName()
        );

        if (sent) {
            return ResponseEntity.ok("Message sent successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message.");
        }
    }
}