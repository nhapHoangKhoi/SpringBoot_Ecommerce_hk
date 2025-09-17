package com.hoangkhoi.springboot_ecommerce.mapper;

import com.hoangkhoi.springboot_ecommerce.dto.request.UserReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.request.UserSignUpReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.RoleRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserInfoRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserSignUpRespDTO;
import com.hoangkhoi.springboot_ecommerce.model.Role;
import com.hoangkhoi.springboot_ecommerce.model.User;
import com.hoangkhoi.springboot_ecommerce.model.UserInfo;
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

    // add mappers for nested objects
    UserInfoRespDTO toDto(UserInfo entity);
    RoleRespDTO toDto(Role entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserReqDTO requestDto, @MappingTarget User existedEntity);
}
