package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.response.RatingRespDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface RatingService {
    Page<RatingRespDTO> getRatingsByProduct(UUID productId, int page, int limit);
}
