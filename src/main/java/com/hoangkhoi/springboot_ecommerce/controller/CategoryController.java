package com.hoangkhoi.springboot_ecommerce.controller;

import com.hoangkhoi.springboot_ecommerce.dto.request.CategoryReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.CategoryRespDTO;
import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import com.hoangkhoi.springboot_ecommerce.service.impl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Get list categories")
    public ResponseEntity<ApiResponse<List<CategoryRespDTO>>> getAllCategories() {
        List<CategoryRespDTO> categories = categoryService.getAllCategories();

        ApiResponse<List<CategoryRespDTO>> response = new ApiResponse<>(
                true,
                "Get list categories successfully!",
                categories
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<ApiResponse<CategoryRespDTO>> createCategory(@RequestBody @Valid CategoryReqDTO request) {
        CategoryRespDTO categoryResponse = categoryService.createCategory(request);

        ApiResponse<CategoryRespDTO> response = new ApiResponse<>(
                true,
                String.format("Create category %s successfully!", request.getName()),
                categoryResponse
        );

        return ResponseEntity.ok(response);
    }
}
