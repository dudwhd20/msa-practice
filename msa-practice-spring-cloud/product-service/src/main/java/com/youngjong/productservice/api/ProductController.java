package com.youngjong.productservice.api;


import com.youngjong.productservice.application.command.RegisterProductCommand;
import com.youngjong.productservice.application.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Long> registerProduct(@RequestBody RegisterProductRequest request) {
        RegisterProductCommand command = new RegisterProductCommand(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity()
        );

        Long productId = productService.registerProduct(command);
        return ResponseEntity.ok(productId);
    }

    @GetMapping
    public ResponseEntity<List<ProductSummaryResponse>> getAllProducts() {
        List<ProductSummaryResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(@PathVariable Long id) {
        ProductDetailResponse response = productService.getProductDetail(id);
        return ResponseEntity.ok(response);
    }


}
