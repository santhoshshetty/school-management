package com.gabriels.school.annotation;

import com.gabriels.school.validations.PasswordStrengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Retention(RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface PasswordValidator {

    String message() default "Please chose a strong password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
