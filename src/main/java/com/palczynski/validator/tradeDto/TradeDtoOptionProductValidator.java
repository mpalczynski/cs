package com.palczynski.validator.tradeDto;


import com.palczynski.dto.TradeDto;
import com.palczynski.validator.ValidationHelper;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidatorContext;

import static com.palczynski.validator.ErrorCode.*;
import static java.util.Objects.nonNull;

@Component
public class TradeDtoOptionProductValidator implements ValidationHelper<TradeDto> {

    public static final String STYLE_AMERICAN = "AMERICAN";
    public static final String STYLE_EUROPEAN = "EUROPEAN";

    @Override
    public boolean isValid(TradeDto trade, ConstraintValidatorContext context) {
        boolean isValid = true;
        isValid &= isStyleValid(trade, context);
        isValid &= isAmericanStyleExcerciseStartDateValid(trade, context);
        isValid &= isValidExpireDate(trade, context);
        isValid &= isValidPremiumDate(trade, context);
        return isValid;
    }

    private boolean isStyleValid(TradeDto trade, ConstraintValidatorContext context) {
        if(!STYLE_AMERICAN.equals(trade.getStyle()) && !STYLE_EUROPEAN.equals(trade.getStyle())) {
            context.buildConstraintViolationWithTemplate(GENERIC_INVALID_OBJECT)
                    .addPropertyNode("style")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isAmericanStyleExcerciseStartDateValid(TradeDto trade, ConstraintValidatorContext context) {
        boolean isValid = true;
        if(STYLE_AMERICAN.equals(trade.getStyle())) {
            if(nonNull(trade.getExcerciseStartDate())) {
                if(nonNull(trade.getTradeDate()) && !trade.getExcerciseStartDate().isAfter(trade.getTradeDate())) {
                    context.buildConstraintViolationWithTemplate(TRADE_EXCERCISE_DATE_BEFORE_TRADE_DATE)
                            .addPropertyNode("excerciseStartDate")
                            .addConstraintViolation();
                    isValid = false;
                }

                if(nonNull(trade.getExpiryDate()) && !trade.getExcerciseStartDate().isBefore(trade.getExpiryDate())) {
                    context.buildConstraintViolationWithTemplate(TRADE_EXCERCISE_DATE_AFTER_EXPIRY_DATE)
                            .addPropertyNode("excerciseStartDate")
                            .addConstraintViolation();
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    private boolean isValidPremiumDate(TradeDto trade, ConstraintValidatorContext context) {
        if(nonNull(trade.getPremiumDate()) && nonNull(trade.getDeliveryDate())) {
            if(!trade.getPremiumDate().isBefore(trade.getDeliveryDate())) {
                context.buildConstraintViolationWithTemplate(TRADE_PREMIUM_DATE_AFTER_DELIVERY_DATE)
                        .addPropertyNode("premiumDate")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }

    private boolean isValidExpireDate(TradeDto trade, ConstraintValidatorContext context) {
        if(nonNull(trade.getExpiryDate()) && nonNull(trade.getDeliveryDate())) {
            if(!trade.getExpiryDate().isBefore(trade.getDeliveryDate())) {
                context.buildConstraintViolationWithTemplate(TRADE_EXPIRY_DATE_AFTER_DELIVERY_DATE)
                        .addPropertyNode("expiryDate")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }

}
