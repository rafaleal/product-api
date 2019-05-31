package com.rakuten.productapi.dto;

import com.rakuten.productapi.domain.Money;

import java.util.List;

public class ProductDTO {

    private String title;

    private String description;

    private List<String> categories;

    private Money money;

    private List<String> imageUrl;

}
