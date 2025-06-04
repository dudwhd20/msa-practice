package com.youngjong.productservice.api;

public class DecreaseStockRequest {

    private int quantity;

    public DecreaseStockRequest() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
