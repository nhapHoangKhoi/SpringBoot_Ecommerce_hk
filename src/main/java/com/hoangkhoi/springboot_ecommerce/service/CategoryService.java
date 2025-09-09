package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.request.CategoryReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.CategoryRespDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryRespDTO> getAllCategories();
    CategoryRespDTO getCategoryById(UUID id);
    CategoryRespDTO createCategory(CategoryReqDTO request);
    CategoryRespDTO updateCategory(UUID id, CategoryReqDTO request);
}
