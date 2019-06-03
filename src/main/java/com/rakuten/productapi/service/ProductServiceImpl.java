package com.rakuten.productapi.service;

import com.rakuten.productapi.domain.Money;
import com.rakuten.productapi.domain.Product;
import com.rakuten.productapi.dto.ProductDTO;
import com.rakuten.productapi.dto.mapper.ProductMapper;
import com.rakuten.productapi.repository.ProductMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    ProductMongoRepository productMongoRepository;
    ExchangeService exchangeService;
    ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMongoRepository repository, ExchangeService exchangeService, ProductMapper productMapper) {
        this.productMongoRepository = repository;
        this.exchangeService = exchangeService;
        this.productMapper = productMapper;
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        Product product = productMapper.mapProductDtoToProduct(productDTO);
        Money exchangedMoney = this.exchangeService.exchangeAmount(product.getMoney());
        product.setMoney(exchangedMoney);
        return productMongoRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID uuid) {

    }

    @Override
    public Product updateProduct(UUID uuid, List<String> categories) {
        return null;
    }


}
