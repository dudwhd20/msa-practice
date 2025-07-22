package com.youngjong.orderservice.application.event;

public class OrderCancelledIntegrationEvent extends IntegrationEvent<OrderCancelledPayload>{

    public OrderCancelledIntegrationEvent(String traceId, OrderCancelledPayload payload) {
        super("OrderCancelled", "v1", traceId, payload);
    }

}
