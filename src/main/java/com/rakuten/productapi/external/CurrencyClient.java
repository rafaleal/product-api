package com.rakuten.productapi.external;

import com.rakuten.productapi.dto.QuoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.rakuten.productapi.constant.ExternalEndpoints.API_CURRENCY_LATEST;

@FeignClient(name="${feign.name}", url = "${feign.url}")
public interface CurrencyClient {
    @GetMapping(API_CURRENCY_LATEST)
    QuoteDTO getCurrency(@RequestParam String base, @RequestParam String symbols);
}
