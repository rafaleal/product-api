package com.rakuten.productapi.service;

import com.rakuten.productapi.domain.Product;
import com.rakuten.productapi.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product createProduct(ProductDTO product);
    void deleteProduct(UUID uuid);
    Product updateProduct(UUID uuid, List<String> categories);
}
