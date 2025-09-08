package com.hoangkhoi.springboot_ecommerce.repository;

import com.hoangkhoi.springboot_ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
