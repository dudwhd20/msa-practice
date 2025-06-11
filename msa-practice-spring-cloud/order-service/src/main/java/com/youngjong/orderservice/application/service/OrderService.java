package com.youngjong.orderservice.application.service;

import com.youngjong.orderservice.application.command.RegisterOrderCommand;
import com.youngjong.orderservice.domain.model.Order;
import com.youngjong.orderservice.domain.model.OrderItem;
import com.youngjong.orderservice.domain.repository.OrderRepository;
import com.youngjong.orderservice.infrastructure.DecreaseStockRequest;
import com.youngjong.orderservice.infrastructure.ProductClient;
import com.youngjong.orderservice.infrastructure.ProductInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    @Transactional
    public Long registerOrder(RegisterOrderCommand command) {
        // 1. 상품 정보 조회 (스냅샷 저장용)
        ProductInfo product = productClient.getProduct(command.getProductId());

        // 2. 상품 서비스에 재고 차감 요청
        productClient.decreaseStock(command.getProductId(),  new DecreaseStockRequest(command.getQuantity()));

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

}
