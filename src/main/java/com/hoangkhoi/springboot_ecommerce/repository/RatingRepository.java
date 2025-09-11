package com.hoangkhoi.springboot_ecommerce.repository;

import com.hoangkhoi.springboot_ecommerce.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
    Page<Rating> findByProductId(UUID productId, Pageable pageable);
}
