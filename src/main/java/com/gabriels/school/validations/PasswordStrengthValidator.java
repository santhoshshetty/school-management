package com.gabriels.school.validations;

import com.gabriels.school.annotation.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator, String> {//String - on where you want to apply this annotation

    private List<String> weakPasswords;

    @Override
    //Used to build initial set of data
    public void initialize(PasswordValidator passwordValidator) {
        weakPasswords=List.of("12345","qwerty","password");
    }

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext context) {
        return passwordField!=null && (!weakPasswords.contains(passwordField));
    }
}
