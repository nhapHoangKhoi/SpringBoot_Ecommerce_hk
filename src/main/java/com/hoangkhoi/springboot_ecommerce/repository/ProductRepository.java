package com.hoangkhoi.springboot_ecommerce.repository;

import com.hoangkhoi.springboot_ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByCategoryIdAndNameContainingIgnoreCase(UUID categoryId, String productName, Pageable pageable);
}
