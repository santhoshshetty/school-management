package com.gabriels.school.validations;

import com.gabriels.school.annotation.FieldValueMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldValueMatch, Object> { //As we need to pass 2 fields to this implementation, hence passing the value as object from here we extract the field and field match

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldValueMatch constraintAnnotation) {
        this.field=constraintAnnotation.field();
        this.fieldMatch=constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        //BeanWrapperImpl is an implementation of the BeanWrapper interface in Spring Framework,
        // used for introspection and manipulation of Java beans dynamically at runtime.
        Object fieldValue=new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue=new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        if(fieldValue!=null)
            return fieldValue.equals(fieldMatchValue);
        else
            return fieldMatchValue==null;
    }
}
