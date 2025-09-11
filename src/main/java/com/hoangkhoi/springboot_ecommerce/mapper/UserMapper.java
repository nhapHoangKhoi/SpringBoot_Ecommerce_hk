package com.hoangkhoi.springboot_ecommerce.mapper;

import com.hoangkhoi.springboot_ecommerce.dto.request.UserReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.request.UserSignUpReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserSignUpRespDTO;
import com.hoangkhoi.springboot_ecommerce.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserSignUpReqDTO dto);
    UserSignUpRespDTO toDtoForSignUp(User entity);

    UserRespDTO toDto(User entity);
    User toEntity(UserReqDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserReqDTO requestDto, @MappingTarget User existedEntity);
}
