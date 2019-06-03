package com.rakuten.productapi.external;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.rakuten.productapi.dto.QuoteDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"feign.hystrix.enabled=true"})
class CurrencyClientTest {
    @Autowired
    CurrencyClient currencyClient;

    QuoteDTO quoteDTO;

    @Test
    @DisplayName("Given currency base as EUR and BRL")
    public void testSucceedingCurrencyClient() {
        quoteDTO = currencyClient.getCurrency("EUR", "BRL");
        assertNotNull(quoteDTO);
        assertEquals("EUR", quoteDTO.getBase());
        assertNull(quoteDTO.getErrror());
        assertNotNull(quoteDTO.getRates());
        assertNotNull(quoteDTO.getRates().get("BRL"));
        assertNotNull(quoteDTO.getDate());
    }

    @Test
    public void testFailingCurrencyClient() {
        assertThrows(HystrixRuntimeException.class,
                () -> currencyClient.getCurrency("EUR", "EUR"));
    }






}