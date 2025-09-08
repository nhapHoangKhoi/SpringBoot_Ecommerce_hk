package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.request.CategoryReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.CategoryRespDTO;
import com.hoangkhoi.springboot_ecommerce.exception.BadRequestException;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.mapper.CategoryMapper;
import com.hoangkhoi.springboot_ecommerce.model.Category;
import com.hoangkhoi.springboot_ecommerce.repository.CategoryRepository;
import com.hoangkhoi.springboot_ecommerce.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public CategoryRespDTO getCategoryById(UUID id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> {
                    return new NotFoundException(
                            String.format("Id %s not found!", id)
                    );
                });

        CategoryRespDTO categoryResponse = categoryMapper.toDto(category);
        return categoryResponse;
    }

    @Override
    public CategoryRespDTO createCategory(CategoryReqDTO request) {
        if(categoryRepository.findByName(request.getName()).isPresent()) {
            throw new BadRequestException(
                    String.format("%s already exists!", request.getName())
            );
        }

        Category categoryModel = categoryMapper.toEntity(request);
        Category newCategoryModel = categoryRepository.save(categoryModel);

        CategoryRespDTO categoryResponse = categoryMapper.toDto(newCategoryModel);
        return categoryResponse;
    }
}
