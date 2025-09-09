package com.hoangkhoi.springboot_ecommerce.dto.response;

import com.hoangkhoi.springboot_ecommerce.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductRespDTO {
    private UUID id;
    private String name;
    private CategoryRespDTO category;
    private String description;
    private BigDecimal price;
    private ProductStatus status;
    private boolean isFeatured;
    private boolean isDeleted;
    private List<ProductImageRespDTO> productImages;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
