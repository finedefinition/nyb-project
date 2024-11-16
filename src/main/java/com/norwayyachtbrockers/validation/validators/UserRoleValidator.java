package com.norwayyachtbrockers.validation.validators;

import com.norwayyachtbrockers.model.enums.UserRoles;
import com.norwayyachtbrockers.validation.annotations.ValidUserRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UserRoleValidator implements ConstraintValidator<ValidUserRole, String> {

    private String message;

    @Override
    public void initialize(ValidUserRole constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String role, ConstraintValidatorContext context) {
        if (role == null || role.isEmpty()) {
            return true;
        }
        try {
            UserRoles.fromString(role);
            return true;
        } catch (IllegalArgumentException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                   .addConstraintViolation();
            return false;
        }
    }
}
