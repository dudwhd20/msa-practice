package com.youngjong.productservice.application.command;

public class RegisterProductCommand {

    private final String name;
    private final String description;
    private final int price;
    private final int stockQuantity;

    public RegisterProductCommand(String name, String description, int price, int stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
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
}
