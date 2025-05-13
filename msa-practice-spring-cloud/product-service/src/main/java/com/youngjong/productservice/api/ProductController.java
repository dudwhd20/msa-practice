package com.youngjong.productservice.api;


import com.youngjong.productservice.application.command.RegisterProductCommand;
import com.youngjong.productservice.application.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
