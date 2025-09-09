package com.hoangkhoi.springboot_ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryReqDTO {
    @NotBlank(message = "Category name cannot be blank!")
    @Size(max = 100, message = "Category name must not exceed {max} characters!")
    private String name;
}
