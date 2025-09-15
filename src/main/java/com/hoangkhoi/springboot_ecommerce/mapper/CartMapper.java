package com.hoangkhoi.springboot_ecommerce.mapper;

import com.hoangkhoi.springboot_ecommerce.dto.response.CartItemRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.CartRespDTO;
import com.hoangkhoi.springboot_ecommerce.model.Cart;
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

    @Mapping(target = "userId", source = "user.id")
    CartRespDTO toDto(Cart entity);

    List<CartItemRespDTO> toListCartItemRespDTOs(List<CartItem> listEntityCartItems);
}
