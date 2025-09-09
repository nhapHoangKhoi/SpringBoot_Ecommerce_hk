package com.hoangkhoi.springboot_ecommerce.controller;

import com.hoangkhoi.springboot_ecommerce.dto.request.ProductReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import com.hoangkhoi.springboot_ecommerce.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get list products")
    public ResponseEntity<ApiResponse<List<ProductRespDTO>>> getAllProducts() {
        List<ProductRespDTO> products = productService.getAllProducts();

        ApiResponse<List<ProductRespDTO>> response = new ApiResponse<>(
                true,
                "Get list products successfully!",
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
                String.format("Get by id: %s successfully!", id),
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
                String.format("Create product %s successfully!", request.getName()),
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
                String.format("Update product %s successfully!", productResponse.getName()),
                productResponse
        );

        return ResponseEntity.ok(response);
    }
}
