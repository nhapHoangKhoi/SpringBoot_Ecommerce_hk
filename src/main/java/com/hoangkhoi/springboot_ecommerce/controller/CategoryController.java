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
import java.util.UUID;

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

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<ApiResponse<CategoryRespDTO>> getCategoryById(@PathVariable UUID id) {
        CategoryRespDTO category = categoryService.getCategoryById(id);

        ApiResponse<CategoryRespDTO> response = new ApiResponse<>(
                true,
                String.format("Get by id: %s successfully!", id),
                category
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

    @PutMapping("/{id}")
    @Operation(summary = "Update category")
    public ResponseEntity<ApiResponse<CategoryRespDTO>> updateCategory(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryReqDTO request
    ) {
        CategoryRespDTO categoryResponse = categoryService.updateCategory(id, request);

        ApiResponse<CategoryRespDTO> response = new ApiResponse<>(
                true,
                String.format("Update category %s successfully!", categoryResponse.getName()),
                categoryResponse
        );

        return ResponseEntity.ok(response);
    }
}
