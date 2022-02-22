package com.mediacare.util;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,Object> {
    private String first;
    private String second;
    private String message;

    @Override
    public void initialize(FieldMatch fieldMatch) {
        first = fieldMatch.first();
        second = fieldMatch.second();
        message = fieldMatch.message();
    }

    @Override
    public boolean isValid(Object value,final ConstraintValidatorContext context) {

        boolean valid=true;
        try{
            final Object object1  =new BeanWrapperImpl(value).getPropertyValue(first);
            final Object object2  =new BeanWrapperImpl(value).getPropertyValue(second);

            valid = object1 ==null && object2==null|| object1!=null&&object1.equals(object2);
        }catch (final Exception e){

        }
        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(first)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
