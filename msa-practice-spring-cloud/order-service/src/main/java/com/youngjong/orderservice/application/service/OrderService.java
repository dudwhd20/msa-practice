package com.youngjong.orderservice.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youngjong.orderservice.api.response.OrderItemResponse;
import com.youngjong.orderservice.api.response.OrderResponse;
import com.youngjong.orderservice.application.command.RegisterOrderCommand;
import com.youngjong.orderservice.application.event.OrderCancelledIntegrationEvent;
import com.youngjong.orderservice.application.event.OrderCancelledPayload;
import com.youngjong.orderservice.application.port.out.OrderEventPublisher;
import com.youngjong.orderservice.domain.model.Order;
import com.youngjong.orderservice.domain.model.OrderItem;
import com.youngjong.orderservice.domain.outbox.OrderOutboxEvent;
import com.youngjong.orderservice.domain.repository.OrderOutboxEventRepository;
import com.youngjong.orderservice.domain.repository.OrderRepository;
import com.youngjong.orderservice.infrastructure.DecreaseStockRequest;
import com.youngjong.orderservice.infrastructure.ProductClient;
import com.youngjong.orderservice.infrastructure.ProductInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;


    private final ObjectMapper objectMapper;
    private final OrderOutboxEventRepository outboxRepository;


    public OrderService(OrderRepository orderRepository, ProductClient productClient, ObjectMapper objectMapper, OrderOutboxEventRepository outboxRepository) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.objectMapper = objectMapper;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public Long registerOrder(RegisterOrderCommand command) {
        // 1. 상품 정보 조회 (스냅샷 저장용)
        ProductInfo product = productClient.getProduct(command.getProductId());

        // 2. 상품 서비스에 재고 차감 요청
        productClient.decreaseStock(command.getProductId(), new DecreaseStockRequest(command.getQuantity()));

        // 3. 주문 도메인 생성
        Order order = new Order(command.getUserId());
        OrderItem item = new OrderItem(
                command.getProductId(),
                product.getName(),
                command.getQuantity(),
                product.getPrice()
        );
        order.addOrderItem(item);

        // 4. 저장 후 주문 ID 반환
        Order saved = orderRepository.save(order);
        return saved.getId();
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getStatus().name(),
                order.getCreatedAt(),
                itemResponses
        );
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);

        return orders.stream()
                .map(order -> {
                    List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                            .map(item -> new OrderItemResponse(
                                    item.getProductId(),
                                    item.getProductName(),
                                    item.getQuantity(),
                                    item.getPrice()
                            ))
                            .toList();

                    return new OrderResponse(
                            order.getId(),
                            order.getUserId(),
                            order.getStatus().name(),
                            order.getCreatedAt(),
                            itemResponses
                    );
                })
                .toList();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.cancel();

        String traceId = UUID.randomUUID().toString();

        order.getOrderItems().forEach(item -> {
            OrderCancelledPayload payload = new OrderCancelledPayload(
                    order.getId(),
                    item.getProductId(),
                    item.getQuantity()
            );

            OrderCancelledIntegrationEvent event = new OrderCancelledIntegrationEvent(traceId, payload);

//            try {
//                String payloadJson = objectMapper.writeValueAsString(payload);
                outboxRepository.save(new OrderOutboxEvent(
                        event.getEventType(),
                        event.getEventVersion(),
                        event.getTraceId(),
                        payload
                ));
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
        });
    }

}
