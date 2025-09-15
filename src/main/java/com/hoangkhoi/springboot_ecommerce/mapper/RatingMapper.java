package com.hoangkhoi.springboot_ecommerce.mapper;

import com.hoangkhoi.springboot_ecommerce.dto.request.RatingReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.RatingRespDTO;
import com.hoangkhoi.springboot_ecommerce.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    Rating toEntity (RatingReqDTO dto);

    @Mapping(target = "email", source = "user.email")
    RatingRespDTO toDto (Rating entity);

    List<RatingRespDTO> toListRatingDTOs(List<Rating> listEntityRatings);
}
