package com.hoangkhoi.springboot_ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CartRespDTO {
    private UUID id;
    private UUID userId;
    private List<CartItemRespDTO> cartItems;
}
