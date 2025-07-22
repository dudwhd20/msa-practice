package com.youngjong.orderservice.application.port.out;

import com.youngjong.orderservice.application.event.OrderCancelledEvent;
import com.youngjong.orderservice.application.event.OrderCancelledIntegrationEvent;

public interface OrderEventPublisher {
    void publishOrderCancelledEvent(OrderCancelledIntegrationEvent event);
}
