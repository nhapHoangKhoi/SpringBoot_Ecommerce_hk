package com.hoangkhoi.springboot_ecommerce.mapper;

import com.hoangkhoi.springboot_ecommerce.dto.response.ProductImageRespDTO;
import com.hoangkhoi.springboot_ecommerce.model.ProductImage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImageRespDTO toDto(ProductImage entity);

    List<ProductImageRespDTO> toListProductImageDTOs(List<ProductImage> listEntityImages);
}
