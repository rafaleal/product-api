package com.rakuten.productapi.repository;

import com.rakuten.productapi.domain.Money;
import com.rakuten.productapi.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataMongoTest
class ProductMongoRepositoryIT {

    @Autowired
    ProductMongoRepository productMongoRepository;

    private UUID uuid;
    private UUID categoryUuid;

    @BeforeEach
    void setup() {
        uuid = UUID.randomUUID();
        categoryUuid = UUID.randomUUID();
    }

    @Test
    void testSaveMongo() {
        Product product = new Product();
        product.setId(uuid);
        product.setTitle("Doc Martens Boot");
        product.setDescription("First bumper ever invented");

        Money money = new Money(BigDecimal.valueOf(10), Currency.getInstance("EUR"));
        product.setMoney(money);

        product.setCategories(Collections.singletonList("category"));
        product.setImageUrl(Collections.singletonList("https://i1.adis.ws/i/drmartens/11822600?$medium$"));
        product.setCreatedAt(LocalDate.parse("2019-06-04"));

        productMongoRepository.save(product);

        Optional<Product> productRetrieved =  productMongoRepository.findById(uuid);
        productRetrieved.ifPresent(product1 -> assertEquals(product, product1));
    }

    @Test
    void testDeleteMongo() {
        productMongoRepository.deleteById(uuid);

        Optional<Product> productRetrieved =  productMongoRepository.findById(uuid);
        assertFalse(productRetrieved.isPresent());
    }

}