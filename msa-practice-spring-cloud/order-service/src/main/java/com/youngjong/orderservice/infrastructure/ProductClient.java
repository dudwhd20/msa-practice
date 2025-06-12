package com.youngjong.orderservice.infrastructure;

import com.youngjong.orderservice.api.request.IncreaseStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductInfo getProduct(@PathVariable("id") Long id);

    @PatchMapping("/products/{id}/stock")
    void decreaseStock(@PathVariable("id") Long id, @RequestBody DecreaseStockRequest request);

    @PatchMapping("/products/{id}/stock/increase")
    void increaseStock(@PathVariable("id") Long id, @RequestBody IncreaseStockRequest request);
}
