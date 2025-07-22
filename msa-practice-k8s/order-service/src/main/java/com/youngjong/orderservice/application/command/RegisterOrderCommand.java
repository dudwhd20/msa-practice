package com.youngjong.orderservice.application.command;

public class RegisterOrderCommand {
    private Long userId;
    private Long productId;
    private int quantity;


    public RegisterOrderCommand(Long userId, Long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

}
