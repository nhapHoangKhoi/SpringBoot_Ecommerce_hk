package com.hoangkhoi.springboot_ecommerce.controller;

import com.hoangkhoi.springboot_ecommerce.dto.request.CategoryReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.CategoryRespDTO;
import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import com.hoangkhoi.springboot_ecommerce.response.SuccessMessages;
import com.hoangkhoi.springboot_ecommerce.service.impl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    // @GetMapping
    // @Operation(summary = "Get list categories")
    // public ResponseEntity<ApiResponse<List<CategoryRespDTO>>> getAllCategories(
    //         @RequestParam(defaultValue = "") String search
    // ) {
    //     List<CategoryRespDTO> categories = categoryService.getCategoryByName(search);
    //
    //     ApiResponse<List<CategoryRespDTO>> response = new ApiResponse<>(
    //             true,
    //             String.format(SuccessMessages.GET_ALL_SUCCESS, "categories"),
    //             categories
    //     );
    //
    //     return ResponseEntity.ok(response);
    // }
    @GetMapping
    @Operation(summary = "Get list categories")
    public ResponseEntity<ApiResponse<Page<CategoryRespDTO>>> getAllCategories(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit
    ) {
        Page<CategoryRespDTO> categories = categoryService.getCategoryByName(keyword, page, limit);

        ApiResponse<Page<CategoryRespDTO>> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.GET_ALL_SUCCESS, "categories"),
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
                String.format(SuccessMessages.GET_BY_ID_SUCCESS, id),
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
                String.format(SuccessMessages.CREATE_SUCCESS, "category", request.getName()),
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
                String.format(SuccessMessages.UPDATE_SUCCESS, "category", categoryResponse.getName()),
                categoryResponse
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.DELETE_SUCCESS, "category", id),
                null
        );

        return ResponseEntity.ok(response);
    }
}
