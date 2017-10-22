package com.palczynski.validator.tradeDto;


import com.palczynski.dto.TradeDto;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static com.palczynski.util.ConstraintViolationUtil.containsError;
import static com.palczynski.validator.ErrorCode.*;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TradeDtoValidatorTest {


    private static Validator validator;

    @Before
    public void init() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldPassValueDateBeforeTradeDate() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .valueDate(LocalDate.now().plusDays(1))
                .tradeDate(LocalDate.now())
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertFalse(containsError("valueDate", TRADE_VALUE_DATE_BEFORE_TRADE_DATE, result));
    }

    @Test
    public void shouldFailValueDateBeforeTradeDate() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .valueDate(LocalDate.now().minusDays(1))
                .tradeDate(LocalDate.now())
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertTrue(containsError("valueDate", TRADE_VALUE_DATE_BEFORE_TRADE_DATE, result));
    }

    @Test
    public void shouldPassISOCurrencies() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .ccyPair("EURUSD")
                .payCcy("USD")
                .premiumCcy("USD")
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertFalse(containsError("ccyPair", GENERIC_INVALID_OBJECT, result));
        assertFalse(containsError("payCcy", GENERIC_INVALID_OBJECT, result));
        assertFalse(containsError("premiumCcy", GENERIC_INVALID_OBJECT, result));
    }

    @Test
    public void shouldFailISOCurrencies() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .ccyPair("EURasd")
                .payCcy("sUSD")
                .premiumCcy("UdSD")
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertTrue(containsError("ccyPair", GENERIC_INVALID_OBJECT, result));
        assertTrue(containsError("payCcy", GENERIC_INVALID_OBJECT, result));
        assertTrue(containsError("premiumCcy", GENERIC_INVALID_OBJECT, result));
    }

    @Test
    public void shouldPassValueDateAtNotWeekend() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .valueDate(LocalDate.of(2016, 1, 1))
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertFalse(containsError("valueDate", TRADE_VALUE_DATE_AT_WEEKEND, result));
    }

    @Test
    public void shouldFailValueDateAtWeekend() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .valueDate(LocalDate.of(2016, 1, 2))
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertTrue(containsError("valueDate", TRADE_VALUE_DATE_AT_WEEKEND, result));
    }

}
