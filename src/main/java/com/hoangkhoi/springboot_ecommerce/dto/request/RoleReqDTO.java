package com.hoangkhoi.springboot_ecommerce.dto.request;

import com.hoangkhoi.springboot_ecommerce.enums.RoleName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleReqDTO {
    private RoleName roleName;
}
