package com.youngjong.productservice.application.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class OrderCancelledPayload {
    private Long orderId;
    private Long productId;
    private int quantity;

    public OrderCancelledPayload(){}
}
