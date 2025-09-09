package com.hoangkhoi.springboot_ecommerce.mapper;

import com.hoangkhoi.springboot_ecommerce.dto.request.ProductReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import com.hoangkhoi.springboot_ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        uses = { // used for preventing duplicated in "target" folder
                CategoryMapper.class
        }
)
public interface ProductMapper {
    Product toEntity(ProductReqDTO dto);

    ProductRespDTO toDto(Product entity);

    void updateEntityFromDto(ProductReqDTO requestDto, @MappingTarget Product existedEntity);
}
