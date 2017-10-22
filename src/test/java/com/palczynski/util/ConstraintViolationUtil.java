package com.palczynski.util;

import javax.validation.ConstraintViolation;
import java.util.Set;


public class ConstraintViolationUtil {

    public static <T> boolean containsError(String propertyPath, String message, Set<ConstraintViolation<T>> constraintViolation) {
        return constraintViolation.stream()
                .anyMatch(c ->  c.getPropertyPath().toString().equals(propertyPath) && c.getMessageTemplate().equals(message));
    }

}
