package com.youngjong.productservice.domain.repository;

import com.youngjong.productservice.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
