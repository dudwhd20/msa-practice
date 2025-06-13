package com.youngjong.productservice.infra.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.youngjong.productservice.application.event.OrderCancelledEvent;
import com.youngjong.productservice.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCancelledEventConsumer {

    private final ProductService productService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "order-cancelled", groupId = "product-service-group")
    public void consume(String message) {
        try {
            OrderCancelledEvent event = objectMapper.readValue(message, OrderCancelledEvent.class);
            log.info("Consumed OrderCancelledEvent: orderId={}, productId={}, quantity={}",
                    event.getOrderId(), event.getProductId(), event.getQuantity());

            // 재고 복구 처리
            productService.increaseStock(event.getProductId(), event.getQuantity());
        } catch (Exception e) {
            log.error("Failed to process OrderCancelledEvent", e);
        }
    }
}
