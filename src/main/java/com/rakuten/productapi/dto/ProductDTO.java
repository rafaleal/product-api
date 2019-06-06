package com.rakuten.productapi.dto;

import com.rakuten.productapi.domain.Money;

import java.util.List;

public class ProductDTO {

    private String title;

    private String description;

    private List<String> categories;

    private Money money;

    private List<String> imageUrl;

    public ProductDTO() {
    }

    public ProductDTO(String title, String description, List<String> categories, Money money, List<String> imageUrl) {
        this.title = title;
        this.description = description;
        this.categories = categories;
        this.money = money;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

}
