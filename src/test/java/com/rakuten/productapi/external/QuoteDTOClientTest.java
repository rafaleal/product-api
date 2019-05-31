package com.rakuten.productapi.external;

import com.rakuten.productapi.dto.QuoteDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class QuoteDTOClientTest {

    @Autowired
    CurrencyClient currencyClient;

    @Test
    public void testWorkingCurrencyExternalApi() {
        QuoteDTO quoteDTO = new QuoteDTO();
    }

}