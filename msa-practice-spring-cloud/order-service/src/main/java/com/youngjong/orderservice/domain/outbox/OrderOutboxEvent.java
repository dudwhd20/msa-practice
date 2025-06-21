package com.youngjong.orderservice.domain.outbox;

import com.youngjong.orderservice.application.event.OrderCancelledPayload;
import com.youngjong.orderservice.config.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload")
    private OrderCancelledPayload payload;

    private LocalDateTime createdAt = LocalDateTime.now();

    public OrderOutboxEvent(String eventType, String eventVersion, String traceId, OrderCancelledPayload payload) {
        this.eventType = eventType;
        this.eventVersion = eventVersion;
        this.traceId = traceId;
        this.payload = payload;
    }
}
