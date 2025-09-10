package com.hoangkhoi.springboot_ecommerce.repository;

import com.hoangkhoi.springboot_ecommerce.enums.RoleName;
import com.hoangkhoi.springboot_ecommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(RoleName roleName);
}
