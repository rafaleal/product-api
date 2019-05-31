package com.rakuten.productapi.service;

import com.rakuten.productapi.domain.Money;
import com.rakuten.productapi.domain.Rate;
import com.rakuten.productapi.dto.QuoteDTO;
import com.rakuten.productapi.external.CurrencyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class ExchangeServiceImpl implements ExchangeService{

    @Value("${currency.base}")
    String baseCurrency;

    CurrencyClient currencyClient;

    @Autowired
    public ExchangeServiceImpl(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    @Override
    public QuoteDTO findRate(Currency current) {
        return this.currencyClient.getCurrency(baseCurrency, current.getCurrencyCode());
    }

    @Override
    public Money exchangeAmount(Money money) {
        Currency currentCurrency = money.getCurrency();
        if(!this.validateCurrentCurrency(currentCurrency)) {
            QuoteDTO quoteDTO = findRate(currentCurrency);
            Rate rate = new Rate(quoteDTO.getRates().get(money.getCurrency().getCurrencyCode()));
            return new Money(money.getAmount().divide(rate.getValue()), Currency.getInstance(baseCurrency));
        } else {
            return money;
        }
    }

    @Override
    public boolean validateCurrentCurrency(Currency current) {
        return Currency.getInstance(baseCurrency).equals(current);
    }

}
