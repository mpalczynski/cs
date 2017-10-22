package com.palczynski.validator.tradeDto;

import com.palczynski.dto.Product;
import com.palczynski.dto.TradeDto;
import com.palczynski.repository.CustomerRepository;
import com.palczynski.validator.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

import static com.palczynski.validator.ErrorCode.*;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class TradeDtoValidator implements ConstraintValidator<TradeDtoValidation, TradeDto> {

    @Autowired
    private CustomerRepository customerRepo;

    private Map<Product, List<ValidationHelper>> productValidators;

    @Override
    public void initialize(TradeDtoValidation tradeDtoValidation) {
        productValidators = new HashMap<>();
        productValidators.put(Product.VANILLAOPTION, asList(new TradeDtoOptionProductValidator()));
        productValidators.put(Product.SPOT, asList(new TradeDtoSpotForwardProductValidator()));
        productValidators.put(Product.FORWARD, asList(new TradeDtoSpotForwardProductValidator()));
    }

    @Override
    public boolean isValid(TradeDto trade, ConstraintValidatorContext context) {
        boolean isValid = true;
        isValid &= isValueDateBeforeTradeDate(trade, context);
        isValid &= isCurrencyValid(trade, context);
        isValid &= isValueDateSetToWeekendOrBankHoliday(trade, context);
        isValid &= isCustomerSupported(trade, context);
        isValid &= validateSpecificProductType(trade, context);
        return isValid;
    }

    private boolean isValueDateBeforeTradeDate(TradeDto trade, ConstraintValidatorContext context) {
        if(nonNull(trade.getValueDate()) && nonNull(trade.getTradeDate())) {
            if(trade.getValueDate().isBefore(trade.getTradeDate())) {
                context.buildConstraintViolationWithTemplate(TRADE_VALUE_DATE_BEFORE_TRADE_DATE)
                        .addPropertyNode("valueDate")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }

    private boolean isCurrencyValid(TradeDto trade, ConstraintValidatorContext context) {
        boolean isValid = true;
        if(nonNull(trade.getCurrencies())) {
            try {
                Currency.getInstance(trade.getCurrencies().getKey());
                Currency.getInstance(trade.getCurrencies().getValue());
            } catch(IllegalArgumentException e) {
                context.buildConstraintViolationWithTemplate(GENERIC_INVALID_OBJECT)
                        .addPropertyNode("ccyPair")
                        .addConstraintViolation();
                isValid = false;
            }
        }
        if(nonNull(trade.getPayCcy())) {
            try {
                Currency.getInstance(trade.getPayCcy());
            } catch(IllegalArgumentException e) {
                context.buildConstraintViolationWithTemplate(GENERIC_INVALID_OBJECT)
                        .addPropertyNode("payCcy")
                        .addConstraintViolation();
                isValid = false;
            }
        }
        if(nonNull(trade.getPremiumCcy())) {
            try {
                Currency.getInstance(trade.getPremiumCcy());
            } catch(IllegalArgumentException e) {
                context.buildConstraintViolationWithTemplate(GENERIC_INVALID_OBJECT)
                        .addPropertyNode("premiumCcy")
                        .addConstraintViolation();
                isValid = false;
            }
        }
        return isValid;
    }

    private boolean isValueDateSetToWeekendOrBankHoliday(TradeDto trade, ConstraintValidatorContext context) {
        if(nonNull(trade.getValueDate())) {
            if (trade.getValueDate().getDayOfWeek().equals(SATURDAY) || trade.getValueDate().getDayOfWeek().equals(SUNDAY)) {
                context.buildConstraintViolationWithTemplate(TRADE_VALUE_DATE_AT_WEEKEND)
                        .addPropertyNode("valueDate")
                        .addConstraintViolation();
                return false;
            }

            if(nonNull(trade.getCurrencies())) {
                try {
                    Currency currency1 = Currency.getInstance(trade.getCurrencies().getKey());
                    Currency currency2 = Currency.getInstance(trade.getCurrencies().getValue());

                    // TODO: bank holidays
                    // https://www.interactivebrokers.com/en/index.php?f=709

                } catch(IllegalArgumentException e) {
                }
            }
        }
        return true;
    }

    private boolean isCustomerSupported(TradeDto trade, ConstraintValidatorContext context) {
        if(nonNull(trade.getCustomer()) && isNull(customerRepo.findByName(trade.getCustomer()))) {
            context.buildConstraintViolationWithTemplate(GENERIC_INVALID_OBJECT)
                    .addPropertyNode("customer")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean validateSpecificProductType(TradeDto trade, ConstraintValidatorContext context) {
        if(nonNull(trade.getType()) && productValidators.containsKey(trade.getType())) {
            return productValidators.get(trade.getType())
                    .stream()
                    .allMatch(v -> v.isValid(trade, context));
        }
        return true;
    }

}
