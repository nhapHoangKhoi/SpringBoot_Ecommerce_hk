package com.hoangkhoi.springboot_ecommerce.controller;

import com.hoangkhoi.springboot_ecommerce.dto.request.OrderReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.CartRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.OrderRespDTO;
import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import com.hoangkhoi.springboot_ecommerce.response.SuccessMessages;
import com.hoangkhoi.springboot_ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Place an order for a user")
    public ResponseEntity<ApiResponse<OrderRespDTO>> placeOrder(@RequestBody @Valid OrderReqDTO request) {
        OrderRespDTO order = orderService.placeOrder(request.getUserId(), request.getProductIds());

        ApiResponse<OrderRespDTO> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.CREATE_SUCCESS, "order", ""),
                order
        );
        return ResponseEntity.ok(response);
    }
}
