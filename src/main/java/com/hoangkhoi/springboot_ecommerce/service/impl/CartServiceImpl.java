package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.response.CartRespDTO;
import com.hoangkhoi.springboot_ecommerce.exception.ExceptionMessages;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.mapper.CartMapper;
import com.hoangkhoi.springboot_ecommerce.model.Cart;
import com.hoangkhoi.springboot_ecommerce.repository.CartRepository;
import com.hoangkhoi.springboot_ecommerce.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public CartRespDTO getCart(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User ID: " + ExceptionMessages.NOT_FOUND, userId))
                );
        return cartMapper.toDto(cart);
    }
}
