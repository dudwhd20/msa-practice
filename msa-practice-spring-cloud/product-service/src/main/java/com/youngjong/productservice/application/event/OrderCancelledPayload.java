package com.youngjong.productservice.application.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class OrderCancelledPayload {
    private Long orderId;
    private Long productId;
    private int quantity;

    public OrderCancelledPayload(){}
}
