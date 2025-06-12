package com.youngjong.productservice.api;

public class IncreaseStockRequest {
    private int quantity;

    public IncreaseStockRequest() {
    }

    public IncreaseStockRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
