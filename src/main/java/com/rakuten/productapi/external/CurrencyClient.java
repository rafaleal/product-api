package com.rakuten.productapi.external;

import com.rakuten.productapi.dto.QuoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="${feign.name}", url = "${feign.url}")
public interface CurrencyClient {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    QuoteDTO getCurrency(@RequestParam(value = "base") String base, @RequestParam(value = "symbols") String symbols);

}
