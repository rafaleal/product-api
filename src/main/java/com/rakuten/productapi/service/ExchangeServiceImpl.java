package com.rakuten.productapi.service;

import com.rakuten.productapi.domain.Money;
import com.rakuten.productapi.domain.Rate;
import com.rakuten.productapi.dto.QuoteDTO;
import com.rakuten.productapi.external.CurrencyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.Currency;

@Service
public class ExchangeServiceImpl implements ExchangeService{

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeService.class);

    Currency baseCurrency;

    CurrencyClient currencyClient;

    @Autowired
    public ExchangeServiceImpl(CurrencyClient currencyClient, @Value("${currency.base}") String base) {
        this.currencyClient = currencyClient;
        baseCurrency = Currency.getInstance(base);
    }



    @Override
    public Money exchangeAmount(Money money) {
        Currency currentCurrency = money.getCurrency();
        if(!this.validateCurrentCurrency(currentCurrency)) {
            QuoteDTO quoteDTO = getQuote(currentCurrency);
            Rate rate = new Rate(quoteDTO.getRates().get(money.getCurrency().getCurrencyCode()));
            return new Money(money.getAmount().divide(rate.getValue(), baseCurrency.getDefaultFractionDigits(), RoundingMode.HALF_UP), baseCurrency);
        } else {
            return money;
        }
    }

    @Override
    public QuoteDTO getQuote(Currency current) {
        return this.currencyClient.getCurrency(baseCurrency.getCurrencyCode(), current.getCurrencyCode());
    }

    @Override
    public boolean validateCurrentCurrency(Currency current) {
        return baseCurrency.equals(current);
    }

}
