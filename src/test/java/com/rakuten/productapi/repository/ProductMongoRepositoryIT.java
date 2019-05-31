package com.rakuten.productapi.repository;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest()
@ExtendWith(SpringExtension.class)
class ProductMongoRepositoryIT {

//    @DisplayName("Given product to save when save P")

    @Autowired
    private ProductMongoRepository productMongoRepository;

    @Before
    public void setup() { productMongoRepository.deleteAll(); }

    @Test
    public void testMongo() {

    }
}