package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.request.ProductReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;

import java.util.List;

public interface ProductService {
    List<ProductRespDTO> getAllProducts();
    ProductRespDTO createProduct(ProductReqDTO request);
}
