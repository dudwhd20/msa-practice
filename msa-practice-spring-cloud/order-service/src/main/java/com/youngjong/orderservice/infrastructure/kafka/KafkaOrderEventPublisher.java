package com.youngjong.orderservice.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youngjong.orderservice.application.event.OrderCancelledEvent;
import com.youngjong.orderservice.application.event.OrderCancelledIntegrationEvent;
import com.youngjong.orderservice.application.port.out.OrderEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class KafkaOrderEventPublisher implements OrderEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaOrderEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishOrderCancelledEvent(OrderCancelledIntegrationEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-cancelled", json);
            log.info("Kafka Published: {}", json);
        } catch (Exception e) {
            log.error("Kafka Publish Failed", e);
        }
    }

}
