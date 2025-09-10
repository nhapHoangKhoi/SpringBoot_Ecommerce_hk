package com.hoangkhoi.springboot_ecommerce.mapper;

import com.hoangkhoi.springboot_ecommerce.dto.request.UserSignUpReqDTO;
import com.hoangkhoi.springboot_ecommerce.model.UserInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    UserInfo toEntity(UserSignUpReqDTO dto);
}
