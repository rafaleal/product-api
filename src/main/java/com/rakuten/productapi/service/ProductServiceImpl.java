package com.rakuten.productapi.service;

import com.rakuten.productapi.domain.Money;
import com.rakuten.productapi.domain.Product;
import com.rakuten.productapi.dto.ProductDTO;
import com.rakuten.productapi.dto.mapper.ProductMapper;
import com.rakuten.productapi.exception.ProductBadRequestException;
import com.rakuten.productapi.exception.ProductNotFoundException;
import com.rakuten.productapi.repository.ProductMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    ProductMongoRepository productMongoRepository;
    ExchangeService exchangeService;
    ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMongoRepository productMongoRepository, ExchangeService exchangeService, ProductMapper productMapper) {
        this.productMongoRepository = productMongoRepository;
        this.exchangeService = exchangeService;
        this.productMapper = productMapper;
    }

    @Override
    public Product getProduct(UUID id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productMongoRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productMongoRepository.findAll();
    }

    @Override
    public Product createProduct(ProductDTO productDTO) throws ProductBadRequestException {
        if (productDTO.getMoney().getAmount().compareTo(BigDecimal.ZERO) > 0) {
            Product product = productMapper.mapProductDtoToProduct(productDTO);
            product.setCreatedAt(LocalDate.now());
            Money exchangedMoney = this.exchangeService.exchangeAmount(product.getMoney());
            product.setMoney(exchangedMoney);
            product.setId(UUID.randomUUID());


            return productMongoRepository.save(product);
        } else {
            throw new ProductBadRequestException();
        }
    }

    @Override
    public void deleteProduct(UUID id) throws ProductNotFoundException {
        if (productMongoRepository.existsById(id)) {
            productMongoRepository.deleteById(id);
        } else {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public Product addCategoryToProduct(UUID productId, String category) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productMongoRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            optionalProduct.get().getCategories().add(category);
            return productMongoRepository.save(optionalProduct.get());
        } else {
            throw new ProductNotFoundException();
        }
    }

    @Override
    public Product removeCategoryFromProduct(UUID uuid, String category) throws Exception {

        Optional<Product> optionalProduct = productMongoRepository.findById(uuid);
        if (optionalProduct.isPresent()) {
            boolean wasRemoved = optionalProduct.get().getCategories().remove(category);

            if(wasRemoved) {
                return productMongoRepository.save(optionalProduct.get());
            }
            throw new ProductBadRequestException();
        } else {
            throw new ProductNotFoundException();
        }
    }

}
