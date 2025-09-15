package com.hoangkhoi.springboot_ecommerce.controller;

import com.hoangkhoi.springboot_ecommerce.dto.request.CartItemReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.CartRespDTO;
import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import com.hoangkhoi.springboot_ecommerce.response.SuccessMessages;
import com.hoangkhoi.springboot_ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get cart by user ID")
    public ResponseEntity<ApiResponse<CartRespDTO>> getCart(@RequestParam UUID userId) {
        CartRespDTO cart = cartService.getCart(userId);

        ApiResponse<CartRespDTO> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.GET_BY_ID_SUCCESS, userId),
                cart
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Add a product to cart")
    public ResponseEntity<ApiResponse<CartRespDTO>> addItemToCart(@RequestBody @Valid CartItemReqDTO request) {
        CartRespDTO cart = cartService.addItemToCart(request);

        ApiResponse<CartRespDTO> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.UPDATE_SUCCESS, "cart", ""),
                cart
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Operation(summary = "Remove a product from cart")
    public ResponseEntity<ApiResponse<Void>> removeItemFromCart(@RequestBody @Valid CartItemReqDTO request) {
        cartService.removeItemFromCart(request);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.DELETE_SUCCESS, "cart_item", ""),
                null
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cart")
    public ResponseEntity<ApiResponse<Void>> deleteCart(@PathVariable UUID id) {
        cartService.deleteCart(id);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.DELETE_SUCCESS, "cart", ""),
                null
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @Operation(summary = "Update quantity of a product in cart")
    public ResponseEntity<ApiResponse<CartRespDTO>> updateQuantity(@RequestBody @Valid CartItemReqDTO request) {
        CartRespDTO updatedCart = cartService.updateQuantity(request);

        ApiResponse<CartRespDTO> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.UPDATE_SUCCESS, "cart", ""),
                updatedCart
        );
        return ResponseEntity.ok(response);
    }
}
