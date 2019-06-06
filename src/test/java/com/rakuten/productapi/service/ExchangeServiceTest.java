package com.rakuten.productapi.service;

import com.rakuten.productapi.domain.Money;
import com.rakuten.productapi.dto.QuoteDTO;
import com.rakuten.productapi.external.CurrencyClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExchangeServiceTest {

    @DisplayName("Given there is an EUR base currency")
    @Nested
    class EURCurrencyTest {

        @Mock
        CurrencyClient currencyClient;

        ExchangeServiceImpl exchangeService;

        Money money;

        Map<String, BigDecimal> rates;
        QuoteDTO quoteDTO;

        final String EUR = "EUR";
        final String BRL = "BRL";
        Currency eurCurrency = Currency.getInstance(EUR);
        Currency brlCurrency = Currency.getInstance(BRL);

        @BeforeEach
        void setup() {
            exchangeService = new ExchangeServiceImpl(currencyClient, EUR);
        }

        @Nested
        @DisplayName("When the current currency is EUR")
        class EURCurrentCurrency {

            @BeforeEach
            void setup() {
                money = new Money(BigDecimal.valueOf(10), eurCurrency);
            }

            @Test
            @DisplayName("Then it should skip the exchange service and return the same Money")
            void testSameCurrentCurrencyAsBaseCurrency() {
                assertAll("Verify all conditions for EUR currency",
                        () -> assertTrue(exchangeService.validateCurrentCurrency(money.getCurrency())),
                        () -> assertEquals(money, exchangeService.exchangeAmount(money))
                );
            }
        }

        @Nested
        @DisplayName("When the current currency is BRL")
        class BRLCurrentCurrency {
            Money moneyAfterExchange;

            @BeforeEach
            void setup() {
                rates = new HashMap<>();
                rates.put(BRL, BigDecimal.valueOf(4.4462));
                quoteDTO = new QuoteDTO(BRL, rates, LocalDate.parse("2019-05-31"), null);
                when(currencyClient.getCurrency(EUR, BRL)).thenReturn(quoteDTO);
                money = new Money(BigDecimal.TEN, brlCurrency);
                moneyAfterExchange = new Money(BigDecimal.valueOf(2.25), eurCurrency);
            }

            @Test
            @DisplayName("Then it should apply the exchange service and return the converted Money with the amount in EUR")
            void testDifferentCurrencyFromBaseCurrency() {
                assertFalse(exchangeService.validateCurrentCurrency(money.getCurrency()));
                assertEquals(quoteDTO, exchangeService.getQuote(brlCurrency));
                assertEquals(moneyAfterExchange.getAmount(), exchangeService.exchangeAmount(money).getAmount());
            }

        }


    }


}