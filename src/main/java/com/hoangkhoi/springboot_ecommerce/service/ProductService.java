package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.request.ProductReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    // List<ProductRespDTO> getAllProducts();
    Page<ProductRespDTO> getAllProducts(int page, int limit);
    ProductRespDTO getProductById(UUID id);
    ProductRespDTO createProduct(ProductReqDTO request);
    ProductRespDTO updateProduct(UUID id, ProductReqDTO request);
    void deleteProductSoft(UUID id);
    Page<ProductRespDTO> getProductsByFilters(
            String productName,
            UUID categoryId,
            int page,
            int limit
    );
    Page<ProductRespDTO> getFeaturedProducts();
}
