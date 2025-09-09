package com.hoangkhoi.springboot_ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryReqDTO {
    @NotBlank(message = "Category name cannot be blank!")
    @Size(max = 100, message = "Category name must not exceed {max} characters!")
    private String name;

    //----- Start getter, setter -----//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //----- End getter, setter -----//
}
