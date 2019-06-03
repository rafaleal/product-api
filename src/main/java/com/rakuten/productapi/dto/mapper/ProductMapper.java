package com.rakuten.productapi.dto.mapper;

import com.rakuten.productapi.domain.Product;
import com.rakuten.productapi.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class ProductMapper {

    ModelMapper modelMapper;

    @Autowired
    ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product mapProductDtoToProduct(ProductDTO dto) { return modelMapper.map(dto, Product.class); }

}
