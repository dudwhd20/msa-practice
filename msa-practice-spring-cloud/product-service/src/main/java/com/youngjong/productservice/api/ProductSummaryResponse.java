package com.youngjong.productservice.api;

import java.time.LocalDateTime;

public class ProductSummaryResponse {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private LocalDateTime createdAt;


    public ProductSummaryResponse(Long id, String name, int price, int stockQuantity, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
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
