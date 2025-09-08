package com.hoangkhoi.springboot_ecommerce.dto.request;

import java.util.UUID;

public class ProductReqDTO {
    private String name;
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
