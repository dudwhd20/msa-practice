package com.youngjong.orderservice.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youngjong.orderservice.application.command.RegisterOrderCommand;
import com.youngjong.orderservice.domain.model.Order;
import com.youngjong.orderservice.domain.model.OrderItem;
import com.youngjong.orderservice.domain.repository.OrderOutboxEventRepository;
import com.youngjong.orderservice.domain.repository.OrderRepository;
import com.youngjong.orderservice.infrastructure.DecreaseStockRequest;
import com.youngjong.orderservice.infrastructure.ProductClient;
import com.youngjong.orderservice.infrastructure.ProductInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductClient productClient;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private OrderOutboxEventRepository outboxRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerOrder_정상동작() {
        RegisterOrderCommand command = new RegisterOrderCommand(1L, 2L, 3);
        ProductInfo productInfo = new ProductInfo("테스트상품", 1000);
        when(productClient.getProduct(2L)).thenReturn(productInfo);
        doNothing().when(productClient).decreaseStock(eq(2L), any(DecreaseStockRequest.class));
        Order savedOrder = new Order(1L);
        savedOrder.addOrderItem(new OrderItem(2L, "테스트상품", 3, 1000));
        // save()가 반환하는 객체의 id가 실제로 사용됨
        when(orderRepository.save(any(Order.class))).then(invocation -> {
            Order o = invocation.getArgument(0);
            org.springframework.test.util.ReflectionTestUtils.setField(o, "id", 123L);
            return o;
        });

        Long orderId = orderService.registerOrder(command);
        assertThat(orderId).isEqualTo(123L);
    }

    @Test
    void getOrder_존재하지않으면_예외() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> orderService.getOrder(1L));
    }

    @Test
    void getOrdersByUserId_정상동작() {
        Order order = new Order(1L);
        order.addOrderItem(new OrderItem(2L, "테스트상품", 3, 1000));
        when(orderRepository.findAllByUserId(1L)).thenReturn(Collections.singletonList(order));
        assertThat(orderService.getOrdersByUserId(1L)).hasSize(1);
    }

    @Test
    void cancelOrder_존재하지않으면_예외() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> orderService.cancelOrder(1L));
    }
}
