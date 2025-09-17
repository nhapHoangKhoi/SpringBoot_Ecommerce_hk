package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.request.RatingReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.RatingRespDTO;
import com.hoangkhoi.springboot_ecommerce.enums.OrderStatus;
import com.hoangkhoi.springboot_ecommerce.exception.BadRequestException;
import com.hoangkhoi.springboot_ecommerce.exception.ExceptionMessages;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.mapper.RatingMapper;
import com.hoangkhoi.springboot_ecommerce.model.Product;
import com.hoangkhoi.springboot_ecommerce.model.Rating;
import com.hoangkhoi.springboot_ecommerce.model.User;
import com.hoangkhoi.springboot_ecommerce.repository.OrderRepository;
import com.hoangkhoi.springboot_ecommerce.repository.ProductRepository;
import com.hoangkhoi.springboot_ecommerce.repository.RatingRepository;
import com.hoangkhoi.springboot_ecommerce.repository.UserRepository;
import com.hoangkhoi.springboot_ecommerce.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final RatingMapper ratingMapper;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

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

    @Transactional
    @Override
    public RatingRespDTO createRating(RatingReqDTO request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(ExceptionMessages.NOT_FOUND, request.getProductId())
                ));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(ExceptionMessages.NOT_FOUND, request.getUserId())
                ));

        // check if user bought that product
        // if(
        //     !orderRepository.existsByUserIdAndOrderItemsProductIdAndOrderStatus(
        //         request.getUserId(),
        //         request.getProductId(),
        //         OrderStatus.CONFIRMED
        //     )
        // ) {
        //     throw new BadRequestException(
        //             String.format(ExceptionMessages.PRODUCT_NOT_PURCHASED, product.getName())
        //     );
        // }

        // calculate avg rating
        updateProductRating(product, request.getRatingValue());

        Rating rating = ratingMapper.toEntity(request);
        rating.setProduct(product);
        rating.setUser(user);

        return ratingMapper.toDto(ratingRepository.save(rating));
    }

    //----- Helper methods -----//
    private void updateProductRating(Product product, int newRating) {
        int currentCount = product.getRatingCount();
        double currentAvg = product.getAvgRating();

        double newAvg = ((currentAvg * currentCount) + newRating) / (currentCount + 1);

        product.setAvgRating(newAvg);
        product.setRatingCount(currentCount + 1);

        productRepository.save(product);
    }
    //----- End helper methods -----//
}
