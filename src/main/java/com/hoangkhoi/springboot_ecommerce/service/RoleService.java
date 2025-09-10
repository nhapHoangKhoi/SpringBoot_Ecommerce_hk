package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.request.RoleReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.RoleRespDTO;

import java.util.List;

public interface RoleService {
    RoleRespDTO createRole(RoleReqDTO roleReqDTO);
    List<RoleRespDTO> getAllRoles();
}
