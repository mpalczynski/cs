package com.palczynski.validator;

import javax.validation.ConstraintValidatorContext;

public interface ValidationHelper<T> {

    boolean isValid(T var1, ConstraintValidatorContext var2);

}
