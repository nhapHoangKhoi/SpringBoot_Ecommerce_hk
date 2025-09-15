package com.hoangkhoi.springboot_ecommerce.mapper;

import com.hoangkhoi.springboot_ecommerce.dto.response.OrderItemRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.OrderRespDTO;
import com.hoangkhoi.springboot_ecommerce.model.CartItem;
import com.hoangkhoi.springboot_ecommerce.model.Order;
import com.hoangkhoi.springboot_ecommerce.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemRespDTO toDto(OrderItem entity);

    @Mapping(target = "userId", source = "user.id")
    OrderRespDTO toDto(Order entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "product.price")
    OrderItem toOrderItem(CartItem cartItem);

    List<OrderItemRespDTO> toListOrderItemDTOs(List<OrderItem> listEntityOrderItems);
}
