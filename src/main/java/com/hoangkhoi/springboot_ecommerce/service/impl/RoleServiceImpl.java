package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.request.RoleReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.RoleRespDTO;
import com.hoangkhoi.springboot_ecommerce.mapper.RoleMapper;
import com.hoangkhoi.springboot_ecommerce.model.Role;
import com.hoangkhoi.springboot_ecommerce.repository.RoleRepository;
import com.hoangkhoi.springboot_ecommerce.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleRespDTO createRole(RoleReqDTO roleReqDTO) {
        Role role = roleMapper.toEntity(roleReqDTO);

        Role savedRoleModel = roleRepository.save(role);

        RoleRespDTO roleResponse = roleMapper.toDto(savedRoleModel);
        return roleResponse;
    }

    @Override
    public List<RoleRespDTO> getAllRoles() {
        List<RoleRespDTO> roles = roleRepository.findAll()
                .stream()
                .map(roleMapper::toDto)
                .toList();

        return roles;
    }
}
