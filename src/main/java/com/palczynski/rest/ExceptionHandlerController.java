package com.palczynski.rest;


import com.palczynski.dto.ValidationErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.util.Objects.nonNull;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDto processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        ValidationErrorDto errors = new ValidationErrorDto();

        if(nonNull(result.getAllErrors())) {
            result.getAllErrors().forEach(e -> {
                if(e instanceof FieldError) {
                    errors.addMessage(((FieldError) e).getField(), e.getDefaultMessage());
                }
                else {
                    errors.addMessage(e.getObjectName(), e.getDefaultMessage());
                }
            });
        }
        return errors;
    }

}
