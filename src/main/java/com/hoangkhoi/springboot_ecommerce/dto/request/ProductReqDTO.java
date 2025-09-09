package com.hoangkhoi.springboot_ecommerce.dto.request;

import com.hoangkhoi.springboot_ecommerce.enums.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductReqDTO {
    @NotBlank(message = "Product name cannot be blank!")
    @Size(max = 100, message = "Product name must not exceed {max} characters!")
    private String name;

    @NotNull(message = "Category cannot be blank!")
    private UUID categoryId;

    @Size(max = 2000, message = "Description must not exceed {max} characters!")
    private String description;

    @NotNull(message = "Price cannot be blank!")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be >= 0!") // value 0 && inclusive true => value >= 0
    @Digits(integer = 15, fraction = 2)
    private BigDecimal price;

    @NotNull(message = "Product status cannot be blank!")
    private ProductStatus status;

    private boolean isFeatured;
    private boolean isDeleted;
}
