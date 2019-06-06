package com.rakuten.productapi.external;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.rakuten.productapi.dto.QuoteDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"feign.hystrix.enabled=true"})
class CurrencyClientIT {

    @Autowired
    CurrencyClient currencyClient;

    QuoteDTO quoteDTO;

    @Nested
    @DisplayName("Given currency base as EUR")
    class EURBaseCurrency {

        @Nested
        @DisplayName("When current currency is BRL")
        class BRLCurrency {
            @Test
            @DisplayName("Then it should return the quotation and rate")
            void testSucceedingCurrencyClient() {
                quoteDTO = currencyClient.getCurrency("EUR", "BRL");
                assertNotNull(quoteDTO);
                assertEquals("EUR", quoteDTO.getBase());
                assertNull(quoteDTO.getError());
                assertNotNull(quoteDTO.getRates());
                assertNotNull(quoteDTO.getRates().get("BRL"));
                assertNotNull(quoteDTO.getDate());
            }
        }

        @Nested
        @DisplayName("When current currency is the same as the base")
        class EURCurrency {

            @Test
            @DisplayName("Then it should through a HystrixRuntimeException")
            void testFailingCurrencyClient() {
                assertThrows(HystrixRuntimeException.class,
                        () -> currencyClient.getCurrency("EUR", "EUR"));
            }

        }

    }

}