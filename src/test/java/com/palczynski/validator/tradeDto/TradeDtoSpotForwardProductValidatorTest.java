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
import static com.palczynski.validator.ErrorCode.GENERIC_CANNOT_BE_EMPTY;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TradeDtoSpotForwardProductValidatorTest {

    private Validator validator;

    @Before
    public void init() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldPassValueProvided() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.SPOT)
                .valueDate(LocalDate.now())
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertFalse(containsError("valueDate", GENERIC_CANNOT_BE_EMPTY, result));
    }

    @Test
    public void shouldFailValueNotProvided() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .type(Product.FORWARD)
                .valueDate(null)
                .build();

        //when
        Set<ConstraintViolation<TradeDto>> result = validator.validate(tradeDto);

        //then
        assertTrue(result.size() > 0);
        assertTrue(containsError("valueDate", GENERIC_CANNOT_BE_EMPTY, result));
    }

}
