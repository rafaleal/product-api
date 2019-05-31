package com.rakuten.productapi.domain;

import java.math.BigDecimal;

public class Rate {

    private BigDecimal value;

    public Rate(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
