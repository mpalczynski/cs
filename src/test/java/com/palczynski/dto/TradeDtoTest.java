package com.palczynski.dto;


import javafx.util.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TradeDtoTest {

    @Test
    public void shouldParseCcyPair() {
        //given
        TradeDto tradeDto = TradeDto.builder()
                .ccyPair("EURUSD")
                .build();

        //when
        Pair<String, String> result = tradeDto.getCurrencies();

        //then
        assertEquals("EUR", result.getKey());
        assertEquals("USD", result.getValue());
    }

}
