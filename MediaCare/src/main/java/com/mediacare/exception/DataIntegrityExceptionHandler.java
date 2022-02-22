package com.mediacare.exception;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@ResponseBody
public class DataIntegrityExceptionHandler{

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handler(SQLIntegrityConstraintViolationException ex){
        Map<String,String> errors =new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error)->{
//            String fieldName=((FieldError)error).getField();
//            String errorMessage=error.getDefaultMessage();
//            errors.put(fieldName,errorMessage);
//        });
        return ex.getMessage();
    }
}
