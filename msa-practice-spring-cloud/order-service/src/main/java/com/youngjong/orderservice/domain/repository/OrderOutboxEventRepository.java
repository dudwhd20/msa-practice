package com.youngjong.orderservice.domain.repository;

import com.youngjong.orderservice.domain.outbox.OrderOutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOutboxEventRepository extends JpaRepository<OrderOutboxEvent, Long> {
}
