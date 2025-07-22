package com.youngjong.productservice.infra.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youngjong.productservice.application.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class OrderCancelledEventConsumerTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderCancelledEventConsumer consumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consume_OrderCancelledEvent_정상동작() throws Exception {
        // given
        String payloadJson = "{\"productId\":1,\"quantity\":2}";
        String message = "{\"payload\":{\"after\":{\"event_type\":\"OrderCancelled\",\"payload\":\"" + payloadJson.replace("\"", "\\\"") + "\"}}}";

        // when
        consumer.consume(message);

        // then
        verify(productService, times(1)).increaseStock(1L, 2);
    }

    @Test
    void consume_이벤트타입_아닐때_호출안함() throws Exception {
        String message = "{\"payload\":{\"after\":{\"event_type\":\"OtherEvent\",\"payload\":\"{}\"}}}";
        consumer.consume(message);
        verify(productService, never()).increaseStock(anyLong(), anyInt());
    }

    @Test
    void consume_after_null_호출안함() throws Exception {
        String message = "{\"payload\":{}}";
        consumer.consume(message);
        verify(productService, never()).increaseStock(anyLong(), anyInt());
    }
}

