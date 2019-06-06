package com.rakuten.productapi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Document(collection = "products")
public class Product {

    @Id
    private UUID id;

    private String title;

    private String description;

    private List<String> categories;

    private Money money;

    private List<String> imageUrl;

    private LocalDate createdAt;

    public Product() {
    }

    public Product(UUID id, String title, String description, List<String> categories, Money money, List<String> imageUrl, LocalDate createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categories = categories;
        this.money = money;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(title, product.title) &&
                Objects.equals(description, product.description) &&
                Objects.equals(categories, product.categories) &&
                Objects.equals(money, product.money) &&
                Objects.equals(imageUrl, product.imageUrl) &&
                Objects.equals(createdAt, product.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, categories, money, imageUrl, createdAt);
    }
}
