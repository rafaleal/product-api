package com.rakuten.productapi.repository;

import com.rakuten.productapi.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductMongoRepository extends MongoRepository<Product, UUID> {
}
