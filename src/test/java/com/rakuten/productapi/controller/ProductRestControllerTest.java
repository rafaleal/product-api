package com.rakuten.productapi.controller;

import com.rakuten.productapi.domain.Money;
import com.rakuten.productapi.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Currency;

import static com.rakuten.productapi.constant.Endpoints.API_PRODUCTS;
import static com.rakuten.productapi.constant.Endpoints.API_V1;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
class ProductRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetProduct() {
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + this.port + API_V1 + API_PRODUCTS + "1", String.class);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    void testPostProduct() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + API_V1 + API_PRODUCTS;
        URI uri = new URI(baseUrl);

    ProductDTO product = new ProductDTO("Title", "Description", Collections.singletonList("categories"), new Money(BigDecimal.TEN, Currency.getInstance("BRL")), Collections.singletonList("images"));

        ResponseEntity<String> entity = restTemplate.postForEntity(uri, product, String.class);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }

//    @Test
//    void testPostProduct() throws Exception {
//        ProductDTO dto = productDTO();
//        this.mockMvc.perform(post(API_V1+API_PRODUCTS)
//        .content(objectMapper.writeValueAsString(dto))
//        .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isCreated())
//                .andReturn();
//    }
//
//    private ProductDTO productDTO() {
//        ProductDTO productDTO = new ProductDTO();
//        productDTO.setTitle("Doc Martens Boot");
//        productDTO.setMoney(new Money(BigDecimal.TEN, Currency.getInstance("BRL")));
//        productDTO.setDescription("First bumper ever invented");
//        productDTO.setImageUrl(Collections.emptyList());
//        productDTO.setCategories(Collections.singletonList("boots"));
//        return productDTO;
//    }
}