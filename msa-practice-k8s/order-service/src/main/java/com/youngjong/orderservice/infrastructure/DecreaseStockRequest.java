package com.youngjong.orderservice.infrastructure;

public class DecreaseStockRequest {
    private int quantity;

    public DecreaseStockRequest() {
    }

    public DecreaseStockRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
