package com.palczynski.validator.tradeDto;


import com.palczynski.dto.Product;
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

public class TradeDtoOptionProductValidatorTest {

    private Validator validator;

    @Before
    public void init() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldPassStyle() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.VANILLAOPTION)
                .style(TradeDtoOptionProductValidator.STYLE_AMERICAN)
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertFalse(containsError("style", GENERIC_INVALID_OBJECT, result));
    }

    @Test
    public void shouldFailStyle() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.VANILLAOPTION)
                .style("asd")
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertTrue(containsError("style", GENERIC_INVALID_OBJECT, result));
    }

    @Test
    public void shouldPassAmericanExcerciseStartDateAfterTradeDate() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.VANILLAOPTION)
                .style(TradeDtoOptionProductValidator.STYLE_AMERICAN)
                .excerciseStartDate(LocalDate.now())
                .tradeDate(LocalDate.now().minusDays(1))
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertFalse(containsError("excerciseStartDate", TRADE_EXCERCISE_DATE_BEFORE_TRADE_DATE, result));
    }

    @Test
    public void shouldFailAmericanExcerciseStartDateAfterTradeDate() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.VANILLAOPTION)
                .style(TradeDtoOptionProductValidator.STYLE_AMERICAN)
                .excerciseStartDate(LocalDate.now())
                .tradeDate(LocalDate.now().plusDays(1))
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertTrue(containsError("excerciseStartDate", TRADE_EXCERCISE_DATE_BEFORE_TRADE_DATE, result));
    }

    @Test
    public void shouldPassAmericanExcerciseStartDateBeforeExpiryDate() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.VANILLAOPTION)
                .style(TradeDtoOptionProductValidator.STYLE_AMERICAN)
                .excerciseStartDate(LocalDate.now())
                .expiryDate(LocalDate.now().plusDays(1))
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertFalse(containsError("excerciseStartDate", TRADE_EXCERCISE_DATE_AFTER_EXPIRY_DATE, result));
    }

    @Test
    public void shouldFailAmericanExcerciseStartDateBeforeExpiryDate() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.VANILLAOPTION)
                .style(TradeDtoOptionProductValidator.STYLE_AMERICAN)
                .excerciseStartDate(LocalDate.now())
                .expiryDate(LocalDate.now().minusDays(1))
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertTrue(containsError("excerciseStartDate", TRADE_EXCERCISE_DATE_AFTER_EXPIRY_DATE, result));
    }

    @Test
    public void shouldPassPremiumDateBeforeDeliveryDate() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.VANILLAOPTION)
                .style(TradeDtoOptionProductValidator.STYLE_AMERICAN)
                .premiumDate(LocalDate.now())
                .deliveryDate(LocalDate.now().plusDays(1))
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertFalse(containsError("premiumDate", TRADE_PREMIUM_DATE_AFTER_DELIVERY_DATE, result));
    }

    @Test
    public void shouldFailPremiumDateBeforeDeliveryDate() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.VANILLAOPTION)
                .style(TradeDtoOptionProductValidator.STYLE_AMERICAN)
                .premiumDate(LocalDate.now())
                .deliveryDate(LocalDate.now().minusDays(1))
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertTrue(containsError("premiumDate", TRADE_PREMIUM_DATE_AFTER_DELIVERY_DATE, result));
    }

    @Test
    public void shouldPassExpiryDateBeforeDeliveryDate() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.VANILLAOPTION)
                .style(TradeDtoOptionProductValidator.STYLE_AMERICAN)
                .expiryDate(LocalDate.now())
                .deliveryDate(LocalDate.now().plusDays(1))
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertFalse(containsError("expiryDate", TRADE_EXPIRY_DATE_AFTER_DELIVERY_DATE, result));
    }

    @Test
    public void shouldFailExpiryDateBeforeDeliveryDate() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.VANILLAOPTION)
                .style(TradeDtoOptionProductValidator.STYLE_AMERICAN)
                .expiryDate(LocalDate.now())
                .deliveryDate(LocalDate.now().minusDays(1))
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertTrue(containsError("expiryDate", TRADE_EXPIRY_DATE_AFTER_DELIVERY_DATE, result));
    }

}
