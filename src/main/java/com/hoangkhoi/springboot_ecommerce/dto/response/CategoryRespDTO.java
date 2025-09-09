package com.hoangkhoi.springboot_ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CategoryRespDTO {
    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
