package com.hoangkhoi.springboot_ecommerce.dto.response;

import com.hoangkhoi.springboot_ecommerce.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderRespDTO {
    private UUID id;
    private UUID userId;
    private BigDecimal total;
    private OrderStatus status;
    private LocalDateTime createdOn;
    private List<OrderItemRespDTO> orderItems;
}
