package com.rakuten.productapi.service;

import com.rakuten.productapi.domain.Money;
import com.rakuten.productapi.domain.Product;
import com.rakuten.productapi.repository.ProductMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    ProductMongoRepository productMongoRepository;
    ExchangeService exchangeService;

    @Autowired
    public ProductService(ProductMongoRepository repository, ExchangeService exchangeService) {
        this.productMongoRepository = repository;
        this.exchangeService = exchangeService;
    }

    public Product createProduct(Product product) {
        Money exchangedMoney = this.exchangeService.exchangeAmount(product.getMoney());
        product.setMoney(exchangedMoney);
        return productMongoRepository.save(product);
    }
}
