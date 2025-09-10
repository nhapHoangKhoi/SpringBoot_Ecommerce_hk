package com.hoangkhoi.springboot_ecommerce.model;

import com.hoangkhoi.springboot_ecommerce.enums.RoleName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "role_name", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
