package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.request.CategoryReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.CategoryRespDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryRespDTO> getAllCategories();
    // List<CategoryRespDTO> getCategoryByName(String name);
    Page<CategoryRespDTO> getCategoryByName(String name, int page, int limit);
    CategoryRespDTO getCategoryById(UUID id);
    CategoryRespDTO createCategory(CategoryReqDTO request);
    CategoryRespDTO updateCategory(UUID id, CategoryReqDTO request);
    void deleteCategory(UUID id);
}
