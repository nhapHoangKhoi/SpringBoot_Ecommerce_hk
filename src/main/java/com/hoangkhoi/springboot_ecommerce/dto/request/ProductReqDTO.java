package com.hoangkhoi.springboot_ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductReqDTO {
    @NotBlank(message = "Product name cannot be blank!")
    @Size(max = 100, message = "Product name must not exceed {max} characters!")
    private String name;

    @NotNull(message = "Category cannot be blank!")
    private UUID categoryId;
}
