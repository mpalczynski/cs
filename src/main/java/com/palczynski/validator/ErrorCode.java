package com.palczynski.validator;


public class ErrorCode {

    public static final String GENERIC_CANNOT_BE_EMPTY = "{generic.cannot_be_empty}";
    public static final String GENERIC_INVALID_OBJECT = "{generic.invalid_object}";

    public static final String TRADE_PRODUCT_INVALID = "{trade.product.invalid}";
    public static final String TRADE_VALUE_DATE_AT_WEEKEND = "{trade.value_date.cannot_be_at_the_weekend}";
    public static final String TRADE_VALUE_DATE_IS_BANK_HOLIDAY = "{trade.value_date.cannot_be_is_christmas}";
    public static final String TRADE_VALUE_DATE_BEFORE_TRADE_DATE = "{trade.value_date.cannot_be_before_trade_date}";
    public static final String TRADE_EXCERCISE_DATE_BEFORE_TRADE_DATE = "{trade.excercise_date.cannot_be_before_trade_date}";
    public static final String TRADE_EXCERCISE_DATE_AFTER_EXPIRY_DATE = "{trade.excercise_date.cannot_be_after_expiry_date}";
    public static final String TRADE_PREMIUM_DATE_AFTER_DELIVERY_DATE = "{trade.premium_date.cannot_be_after_delivery_date}";
    public static final String TRADE_EXPIRY_DATE_AFTER_DELIVERY_DATE = "{trade.expiry_date.cannot_be_after_delivery_date}";
}
