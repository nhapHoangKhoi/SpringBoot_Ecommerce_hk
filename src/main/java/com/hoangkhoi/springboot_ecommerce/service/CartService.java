package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.response.CartRespDTO;

import java.util.UUID;

public interface CartService {
    CartRespDTO getCart(UUID userId);
}
