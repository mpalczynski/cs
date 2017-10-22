package com.palczynski.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import static java.util.Objects.nonNull;

public enum Product {
        SPOT, FORWARD, VANILLAOPTION;

        @JsonCreator
        public static Product fromValue(String value) {
            try {
                if(nonNull(value)) {
                    return Product.valueOf(value.toUpperCase());
                }
            } catch(IllegalArgumentException e) {
            }
            return null;
        }

}