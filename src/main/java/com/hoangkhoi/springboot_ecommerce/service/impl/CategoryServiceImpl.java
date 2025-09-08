package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.response.CategoryRespDTO;
import com.hoangkhoi.springboot_ecommerce.mapper.CategoryMapper;
import com.hoangkhoi.springboot_ecommerce.repository.CategoryRepository;
import com.hoangkhoi.springboot_ecommerce.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryRespDTO> getAllCategories() {
        List<CategoryRespDTO> categories = categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        return categories;
    }
}
