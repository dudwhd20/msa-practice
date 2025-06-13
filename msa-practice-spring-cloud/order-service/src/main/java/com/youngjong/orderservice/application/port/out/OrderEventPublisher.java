package com.youngjong.orderservice.application.port.out;

import com.youngjong.orderservice.application.event.OrderCancelledEvent;

public interface OrderEventPublisher {
    void publishOrderCancelledEvent(OrderCancelledEvent event);
}
