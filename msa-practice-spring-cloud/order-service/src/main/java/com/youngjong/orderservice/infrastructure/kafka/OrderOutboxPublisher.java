package com.youngjong.orderservice.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youngjong.orderservice.application.event.OrderCancelledIntegrationEvent;
import com.youngjong.orderservice.application.event.OrderCancelledPayload;
import com.youngjong.orderservice.domain.outbox.OrderOutboxEvent;
import com.youngjong.orderservice.domain.repository.OrderOutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderOutboxPublisher {

    private final OrderOutboxEventRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 3000)
    public void publishOutboxEvents() {
        List<OrderOutboxEvent> events = outboxRepository.findAll();

        for (OrderOutboxEvent event : events) {
            try {
                // Kafka 전송
                kafkaTemplate.send("order-cancelled", buildKafkaMessage(event));
                log.info("Kafka Published from Outbox: {}", event.getId());

                // 전송 성공 후 Outbox 삭제 (보통 Soft Delete 또는 상태 변경을 권장)
                outboxRepository.delete(event);

            } catch (Exception e) {
                log.error("Kafka publish failed for Outbox event id={}", event.getId(), e);
            }
        }
    }

    private String buildKafkaMessage(OrderOutboxEvent event) throws Exception {
        OrderCancelledIntegrationEvent integrationEvent = new OrderCancelledIntegrationEvent(
                event.getTraceId(),
                objectMapper.readValue(event.getPayload(), OrderCancelledPayload.class)
        );
        return objectMapper.writeValueAsString(integrationEvent);
    }
}
