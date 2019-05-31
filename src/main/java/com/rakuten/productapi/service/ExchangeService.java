package com.rakuten.productapi.service;

import com.rakuten.productapi.domain.Money;
import com.rakuten.productapi.dto.QuoteDTO;

import java.util.Currency;

public interface ExchangeService {

    QuoteDTO findRate(Currency currency);

    Money exchangeAmount(Money money);

    boolean validateCurrentCurrency(Currency current);

}
