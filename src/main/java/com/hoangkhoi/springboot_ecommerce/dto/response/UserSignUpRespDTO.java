package com.hoangkhoi.springboot_ecommerce.dto.response;

import com.hoangkhoi.springboot_ecommerce.model.Role;
import com.hoangkhoi.springboot_ecommerce.model.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserSignUpRespDTO {
    private UUID id;
    private String email;
    private List<UserInfo> userInfos;
    private Set<Role> roles;
}
