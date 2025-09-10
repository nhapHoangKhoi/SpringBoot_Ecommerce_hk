package com.hoangkhoi.springboot_ecommerce.dto.response;

import com.hoangkhoi.springboot_ecommerce.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserRespDTO {
    private UUID id;
    private String email;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Role> roles;
}
