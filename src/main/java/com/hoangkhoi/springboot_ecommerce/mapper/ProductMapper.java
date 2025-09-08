package com.hoangkhoi.springboot_ecommerce.mapper;

import com.hoangkhoi.springboot_ecommerce.dto.request.ProductReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import com.hoangkhoi.springboot_ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = { // used for preventing duplicated in "target" folder
                CategoryMapper.class
        }
)
public interface ProductMapper {
    @Mapping(target = "name", source = "name")
        // @Mapping(target = "category.id", source = "categoryId") // only for more clear, readability,
        // this will make duplicated in "target" folder file
    Product toEntity(ProductReqDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ProductRespDTO toDto(Product entity);
}
