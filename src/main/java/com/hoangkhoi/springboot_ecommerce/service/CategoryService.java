package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.response.CategoryRespDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryRespDTO> getAllCategories();
}
