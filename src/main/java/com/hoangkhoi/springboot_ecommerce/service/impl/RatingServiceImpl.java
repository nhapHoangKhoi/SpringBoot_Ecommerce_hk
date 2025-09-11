package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.response.RatingRespDTO;
import com.hoangkhoi.springboot_ecommerce.exception.ExceptionMessages;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.mapper.RatingMapper;
import com.hoangkhoi.springboot_ecommerce.repository.ProductRepository;
import com.hoangkhoi.springboot_ecommerce.repository.RatingRepository;
import com.hoangkhoi.springboot_ecommerce.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final RatingMapper ratingMapper;

    @Override
    public Page<RatingRespDTO> getRatingsByProduct(UUID productId, int page, int limit) {
        productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Product id: " + ExceptionMessages.NOT_FOUND, productId))
                );

        // ----- Pagination ----- //
        if(page <= 0) {
            page = 1;
        }

        long totalRecords = ratingRepository.count();
        int totalPages = (int) Math.ceil((double) totalRecords / limit);
        if(page > totalPages && totalPages > 0) {
            page = totalPages;
        }

        Pageable pageable = PageRequest.of(
                page - 1, // spring data uses 0-based page index, so subtract 1
                limit,
                Sort.by("createdAt").descending()
        );
        // ----- End pagination ----- //

        Page<RatingRespDTO> ratings = ratingRepository
                .findByProductId(productId, pageable)
                .map(ratingMapper::toDto);

        return ratings;
    }
}
