package com.youngjong.productservice.api;

import java.time.LocalDateTime;

public class ProductDetailResponse {

    private Long id;
    private String name;
    private String description;
    private int price;
    private int stockQuantity;
    private LocalDateTime createdAt;

    public ProductDetailResponse(Long id, String name, String description, int price, int stockQuantity, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
