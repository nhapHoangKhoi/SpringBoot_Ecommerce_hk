package com.hoangkhoi.springboot_ecommerce.controller;

import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import com.hoangkhoi.springboot_ecommerce.response.ApiResponse;
import com.hoangkhoi.springboot_ecommerce.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
