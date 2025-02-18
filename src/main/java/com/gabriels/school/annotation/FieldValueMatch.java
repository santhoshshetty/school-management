package com.gabriels.school.annotation;

import com.gabriels.school.validations.FieldsValueMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD,FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE, TYPE})
public @interface FieldValueMatch {

    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default{};
    String message() default "Fields values don't match!";
    String field();
    String fieldMatch();

    @Retention(RetentionPolicy.RUNTIME)
    @Target({TYPE})
    @interface List{
        FieldValueMatch[] value();
    }

}
