package com.hoangkhoi.springboot_ecommerce.mapper;

import com.hoangkhoi.springboot_ecommerce.dto.request.RoleReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.RoleRespDTO;
import com.hoangkhoi.springboot_ecommerce.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleReqDTO dto);

    RoleRespDTO toDto(Role entity);
}
