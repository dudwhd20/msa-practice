package com.youngjong.orderservice.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCancelledPayload {
    private Long orderId;
    private Long productId;
    private int quantity;
}
