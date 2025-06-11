package com.youngjong.orderservice.api;

import com.youngjong.orderservice.api.request.RegisterOrderRequest;
import com.youngjong.orderservice.api.response.OrderResponse;
import com.youngjong.orderservice.application.command.RegisterOrderCommand;
import com.youngjong.orderservice.application.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Long> registerOrder(@RequestBody RegisterOrderRequest request) {
        RegisterOrderCommand command = new RegisterOrderCommand(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity()
        );

        Long orderId = orderService.registerOrder(command);
        return ResponseEntity.ok(orderId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(@RequestParam Long userId) {
        List<OrderResponse> responses = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(responses);
    }

}
