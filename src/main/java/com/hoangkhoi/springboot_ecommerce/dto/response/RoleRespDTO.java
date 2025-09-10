package com.hoangkhoi.springboot_ecommerce.dto.response;

import com.hoangkhoi.springboot_ecommerce.enums.RoleName;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoleRespDTO {
    private UUID id;
    private RoleName roleName;
}
