package com.palczynski.validator.tradeDto;


import com.palczynski.dto.TradeDto;
import com.palczynski.validator.ValidationHelper;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidatorContext;

import static com.palczynski.validator.ErrorCode.GENERIC_CANNOT_BE_EMPTY;
import static java.util.Objects.isNull;

@Component
public class TradeDtoSpotForwardProductValidator implements ValidationHelper<TradeDto> {

    @Override
    public boolean isValid(TradeDto trade, ConstraintValidatorContext context) {
        boolean isValid = true;
        isValid &= isValueProvided(trade, context);
        return isValid;
    }

    private boolean isValueProvided(TradeDto trade, ConstraintValidatorContext context) {
        if(isNull(trade.getValueDate())) {
            context.buildConstraintViolationWithTemplate(GENERIC_CANNOT_BE_EMPTY)
                    .addPropertyNode("valueDate")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
