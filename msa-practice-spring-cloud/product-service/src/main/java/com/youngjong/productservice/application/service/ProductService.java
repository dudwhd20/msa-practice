package com.youngjong.productservice.application.service;

import com.youngjong.productservice.application.command.RegisterProductCommand;
import com.youngjong.productservice.domain.model.Product;
import com.youngjong.productservice.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    // 생성자 주입
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Long registerProduct(RegisterProductCommand command) {
        Product product = new Product(
                command.getName(),
                command.getDescription(),
                command.getPrice(),
                command.getStockQuantity()
        );

        Product saved = productRepository.save(product);
        return saved.getId();
    }

}
