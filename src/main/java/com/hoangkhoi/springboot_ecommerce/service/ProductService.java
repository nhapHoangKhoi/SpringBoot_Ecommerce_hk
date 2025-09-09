package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.request.ProductReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductRespDTO> getAllProducts();
    ProductRespDTO getProductById(UUID id);
    ProductRespDTO createProduct(ProductReqDTO request);
    ProductRespDTO updateProduct(UUID id, ProductReqDTO request);
}
