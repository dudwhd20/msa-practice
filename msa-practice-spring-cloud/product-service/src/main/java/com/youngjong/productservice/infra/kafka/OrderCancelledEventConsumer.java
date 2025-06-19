package com.youngjong.productservice.infra.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.youngjong.productservice.application.event.IntegrationEvent;
import com.youngjong.productservice.application.event.OrderCancelledEvent;
import com.youngjong.productservice.application.event.OrderCancelledPayload;
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
            IntegrationEvent<OrderCancelledPayload> event = objectMapper.readValue(
                    message,
                    objectMapper.getTypeFactory().constructParametricType(
                            IntegrationEvent.class, OrderCancelledPayload.class
                    )
            );

            log.info("Consumed eventType={}, version={}, traceId={}, payload={}",
                    event.getEventType(),
                    event.getEventVersion(),
                    event.getTraceId(),
                    event.getPayload()
            );

            // 재고 복구 처리
            productService.increaseStock(
                    event.getPayload().getProductId(),
                    event.getPayload().getQuantity()
            );

        } catch (Exception e) {
            log.error("Failed to process OrderCancelledIntegrationEvent", e);
        }
    }
}
