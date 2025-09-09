package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.request.CategoryReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.CategoryRespDTO;
import com.hoangkhoi.springboot_ecommerce.exception.BadRequestException;
import com.hoangkhoi.springboot_ecommerce.exception.ExceptionMessages;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.mapper.CategoryMapper;
import com.hoangkhoi.springboot_ecommerce.model.Category;
import com.hoangkhoi.springboot_ecommerce.repository.CategoryRepository;
import com.hoangkhoi.springboot_ecommerce.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

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
    public List<CategoryRespDTO> getCategoryByName(String name) {
        List<CategoryRespDTO> categories = categoryRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        return categories;
    }

    @Override
    public CategoryRespDTO getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundException(
                            String.format("Id " + ExceptionMessages.NOT_FOUND, id)
                    );
                });

        CategoryRespDTO categoryResponse = categoryMapper.toDto(category);
        return categoryResponse;
    }

    @Override
    public CategoryRespDTO createCategory(CategoryReqDTO request) {
        if(categoryRepository.findByName(request.getName()).isPresent()) {
            throw new BadRequestException(
                    String.format(ExceptionMessages.ALREADY_EXISTS, request.getName())
            );
        }

        Category categoryModel = categoryMapper.toEntity(request);
        Category newCategoryModel = categoryRepository.save(categoryModel);

        CategoryRespDTO categoryResponse = categoryMapper.toDto(newCategoryModel);
        return categoryResponse;
    }

    @Override
    public CategoryRespDTO updateCategory(UUID id, CategoryReqDTO request) {
        Category existedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundException(
                            String.format("Id " + ExceptionMessages.NOT_FOUND, id)
                    );
                });

        // check if another category has this name
        if(categoryRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new BadRequestException(
                    String.format(ExceptionMessages.ALREADY_EXISTS, request.getName())
            );
        }

        categoryMapper.updateEntityFromDto(request, existedCategory);

        Category newCategoryModel = categoryRepository.save(existedCategory);
        CategoryRespDTO categoryResponse = categoryMapper.toDto(newCategoryModel);
        return categoryResponse;
    }

    @Override
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundException(
                            String.format("Id " + ExceptionMessages.NOT_FOUND, id)
                    );
                });

        categoryRepository.delete(category);
    }
}
