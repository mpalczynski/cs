package com.palczynski.dto;


import com.palczynski.validator.tradeDto.TradeDtoValidation;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static com.palczynski.validator.ErrorCode.TRADE_PRODUCT_INVALID;
import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TradeDtoValidation
public class TradeDto {

    @NotBlank
    private String customer;

    @Size(min = 6, max = 6)
    @NotNull
    private String ccyPair;

    @NotNull(message = TRADE_PRODUCT_INVALID)
    private Product type;

    @NotNull
    private Double amount1;

    @NotNull
    private Double amount2;

    @NotNull
    private Double rate;

    @NotNull
    private LocalDate tradeDate;

    @NotBlank
    private String legalEntity;

    @NotBlank
    private String trader;

    private String payCcy;

    private String premiumCcy;

    private String style;

    private String direction;

    private String strategy;

    private Double premium;

    private LocalDate valueDate;

    private LocalDate deliveryDate;

    private LocalDate expiryDate;

    private LocalDate excerciseStartDate;

    private LocalDate premiumDate;

    public Pair<String, String> getCurrencies() {
        if(nonNull(ccyPair) && ccyPair.length() == 6) {
            return new Pair<>(ccyPair.substring(0, 3), ccyPair.substring(3, 6));
        }
        return null;
    }

}
