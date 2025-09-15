package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.response.OrderRespDTO;
import com.hoangkhoi.springboot_ecommerce.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderRespDTO placeOrder(UUID userId, List<UUID> productIds);
    OrderRespDTO getOrderById(UUID orderId);
    List<OrderRespDTO> getOrdersByUser(UUID userId);
    OrderRespDTO updateStatusById(UUID orderId, OrderStatus status);
}
