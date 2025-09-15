package com.hoangkhoi.springboot_ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CartItemRespDTO {
    private UUID id;
    private UUID productId;
    private String productName;
    private List<ProductImageRespDTO> productImages;
    private int quantity;
    private BigDecimal price;
}
