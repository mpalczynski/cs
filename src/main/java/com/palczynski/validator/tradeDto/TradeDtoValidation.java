package com.palczynski.validator.tradeDto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TradeDtoValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TradeDtoValidation {

    String message() default "{error}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
