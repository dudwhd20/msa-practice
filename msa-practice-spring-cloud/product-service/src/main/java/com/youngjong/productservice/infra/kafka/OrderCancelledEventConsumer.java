package com.youngjong.productservice.infra.kafka;


import com.fasterxml.jackson.databind.JsonNode;
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

    @KafkaListener(topics = "orderdb-cdc.public.order_outbox_event", groupId = "product-service-group")
    public void consume(String message) {
        try {
            // Debezium이 발행하는 기본 구조 안에는 before/after 상태가 들어있다
            log.info("Input Message : {}" , message);
            JsonNode jsonNode = objectMapper.readTree(message);
            JsonNode after = jsonNode.get("payload").get("after");

            if (after != null) {
                String eventType = after.get("event_type").asText();
                String payloadJson = after.get("payload").asText();

                if ("OrderCancelled".equals(eventType)) {
                    OrderCancelledPayload payload = objectMapper.readValue(payloadJson, OrderCancelledPayload.class);

                    log.info("Recovered stock for productId={}, quantity={}",
                            payload.getProductId(), payload.getQuantity());

                    productService.increaseStock(payload.getProductId(), payload.getQuantity());
                }
            }
        } catch (Exception e) {
            log.error("Failed to process CDC message", e);
        }
    }
}
