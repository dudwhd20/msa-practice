package com.youngjong.orderservice.infrastructure;

public class ProductInfo {
    public ProductInfo(String name, int price) {
        this.name = name;
        this.price = price;
    }

    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
