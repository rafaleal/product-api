package com.rakuten.productapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class QuoteDTO {

    private String base;

    private Map<String, BigDecimal> rates;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String error;

    public QuoteDTO() {
    }

    public QuoteDTO(String base, Map<String, BigDecimal> rates, LocalDate date, String error) {
        this.base = base;
        this.rates = rates;
        this.date = date;
        this.error = error;
    }

    public String getBase() {
        return base;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getErrror() {
        return error;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setErrror(String error) {
        this.error = error;
    }
}
