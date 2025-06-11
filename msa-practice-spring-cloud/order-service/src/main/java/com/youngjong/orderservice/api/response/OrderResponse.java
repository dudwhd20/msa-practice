package com.youngjong.orderservice.api.response;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private Long userId;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> orderItems;

    public OrderResponse(Long id, Long userId, String status, LocalDateTime createdAt, List<OrderItemResponse> orderItems) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }
}
