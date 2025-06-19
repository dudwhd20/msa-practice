package com.youngjong.orderservice.domain.outbox;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_outbox_event")
public class OrderOutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;
    private String eventVersion;
    private String traceId;

    @Lob
    private String payload;  // JSON 문자열

    private LocalDateTime createdAt = LocalDateTime.now();

    public OrderOutboxEvent(String eventType, String eventVersion, String traceId, String payload) {
        this.eventType = eventType;
        this.eventVersion = eventVersion;
        this.traceId = traceId;
        this.payload = payload;
    }
}
