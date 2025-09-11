package com.hoangkhoi.springboot_ecommerce.controller;

import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.RatingRespDTO;
import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import com.hoangkhoi.springboot_ecommerce.response.SuccessMessages;
import com.hoangkhoi.springboot_ecommerce.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ratings")
@AllArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    @Operation(summary = "Get ratings by product ID")
    public ResponseEntity<ApiResponse<Page<RatingRespDTO>>> getRatingsByProductId(
            @RequestParam UUID productId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit
    ) {
        Page<RatingRespDTO> ratings = ratingService.getRatingsByProduct(productId, page, limit);

        ApiResponse<Page<RatingRespDTO>> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.GET_ALL_SUCCESS, "ratings"),
                ratings
        );
        return ResponseEntity.ok(response);
    }
}
