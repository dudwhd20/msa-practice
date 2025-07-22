package com.youngjong.orderservice.api.response;

public class OrderItemResponse {

    private Long productId;
    private String productName;
    private int quantity;
    private int price;
    private int totalPrice;

    public OrderItemResponse(Long productId, String productName, int quantity, int price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = price * quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
