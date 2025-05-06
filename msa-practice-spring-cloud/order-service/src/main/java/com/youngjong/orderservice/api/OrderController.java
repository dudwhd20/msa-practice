package com.youngjong.orderservice.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("/{id}")
    public String getOrder(@PathVariable String id) {
        return "Order: " + id;
    }

    @PostMapping
    public String createOrder() {
        return "Order created!";
    }

 }
