package com.rakuten.productapi.service;

import com.rakuten.productapi.domain.Product;
import com.rakuten.productapi.dto.ProductDTO;
import com.rakuten.productapi.exception.ProductBadRequestException;
import com.rakuten.productapi.exception.ProductNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product getProduct(UUID id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product createProduct(ProductDTO product) throws ProductBadRequestException;
    void deleteProduct(UUID id) throws ProductNotFoundException;
    Product addCategoryToProduct(UUID productId, String category) throws ProductNotFoundException;
    Product removeCategoryFromProduct(UUID uuid, String category) throws Exception;
}
