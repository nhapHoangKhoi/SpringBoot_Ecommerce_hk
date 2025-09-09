package com.hoangkhoi.springboot_ecommerce.controller;

import com.hoangkhoi.springboot_ecommerce.dto.request.ProductReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import com.hoangkhoi.springboot_ecommerce.response.SuccessMessages;
import com.hoangkhoi.springboot_ecommerce.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    @GetMapping
    @Operation(summary = "Get list products")
    public ResponseEntity<ApiResponse<List<ProductRespDTO>>> getAllProducts() {
        List<ProductRespDTO> products = productService.getAllProducts();

        ApiResponse<List<ProductRespDTO>> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.GET_ALL_SUCCESS, "products"),
                products
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<ApiResponse<ProductRespDTO>> getProductById(@PathVariable UUID id) {
        ProductRespDTO product = productService.getProductById(id);

        ApiResponse<ProductRespDTO> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.GET_BY_ID_SUCCESS, id),
                product
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<ApiResponse<ProductRespDTO>> createProduct(@RequestBody @Valid ProductReqDTO request) {
        ProductRespDTO product = productService.createProduct(request);

        ApiResponse<ProductRespDTO> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.CREATE_SUCCESS, "product", request.getName()),
                product
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    public ResponseEntity<ApiResponse<ProductRespDTO>> updateProduct(
            @PathVariable UUID id,
            @RequestBody @Valid ProductReqDTO request
    ) {
        ProductRespDTO productResponse = productService.updateProduct(id, request);

        ApiResponse<ProductRespDTO> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.UPDATE_SUCCESS, "product", productResponse.getName()),
                productResponse
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product soft")
    public ResponseEntity<ApiResponse<Void>> deleteProductSoft(@PathVariable UUID id) {
        productService.deleteProductSoft(id);

        ApiResponse<Void> response = new ApiResponse<>(
                true,
                String.format(SuccessMessages.DELETE_SUCCESS, "soft product", id),
                null
        );

        return ResponseEntity.ok(response);
    }
}
