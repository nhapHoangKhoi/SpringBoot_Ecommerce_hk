package com.hoangkhoi.springboot_ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class ProductReqDTO {
    @NotBlank(message = "Product name cannot be blank!")
    @Size(max = 100, message = "Product name must not exceed {max} characters!")
    private String name;

    @NotNull(message = "Category cannot be blank!")
    private UUID categoryId;

    //----- Start getter, setter -----//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    //----- End getter, setter -----//
}
