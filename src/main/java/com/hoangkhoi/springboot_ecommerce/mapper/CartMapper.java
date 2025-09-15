package com.hoangkhoi.springboot_ecommerce.mapper;

import com.hoangkhoi.springboot_ecommerce.dto.response.CartItemRespDTO;
import com.hoangkhoi.springboot_ecommerce.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        ProductImageMapper.class
})
public interface CartMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "productImages", source = "product.productImages")
    CartItemRespDTO toDto(CartItem entity);

    List<CartItemRespDTO> toListCartItemRespDTOs(List<CartItem> listEntityCartItems);
}
