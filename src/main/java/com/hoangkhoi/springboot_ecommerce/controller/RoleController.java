package com.hoangkhoi.springboot_ecommerce.controller;

import com.hoangkhoi.springboot_ecommerce.dto.request.RoleReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.RoleRespDTO;
import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import com.hoangkhoi.springboot_ecommerce.response.SuccessMessages;
import com.hoangkhoi.springboot_ecommerce.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@AllArgsConstructor
public class RoleController {
    private RoleService roleService;

    @GetMapping
    @Operation(summary = "Get all roles")
    public ResponseEntity<ApiResponse<List<RoleRespDTO>>> getAllRoles() {
        List<RoleRespDTO> roles = roleService.getAllRoles();

        ApiResponse<List<RoleRespDTO>> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.GET_ALL_SUCCESS, "roles"),
                roles
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create a new role")
    public ResponseEntity<ApiResponse<RoleRespDTO>> createRole(@RequestBody @Valid RoleReqDTO request) {
        RoleRespDTO role = roleService.createRole(request);

        ApiResponse<RoleRespDTO> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.CREATE_SUCCESS, "role", request.getRoleName()),
                role
        );
        return ResponseEntity.ok(response);
    }
}
