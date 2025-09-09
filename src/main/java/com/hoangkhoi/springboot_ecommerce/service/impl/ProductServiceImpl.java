package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.request.ProductReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.mapper.ProductMapper;
import com.hoangkhoi.springboot_ecommerce.model.Category;
import com.hoangkhoi.springboot_ecommerce.model.Product;
import com.hoangkhoi.springboot_ecommerce.repository.CategoryRepository;
import com.hoangkhoi.springboot_ecommerce.repository.ProductRepository;
import com.hoangkhoi.springboot_ecommerce.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductRespDTO> getAllProducts() {
        List<ProductRespDTO> products = productRepository
                .findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();

        return products;
    }

    @Override
    public ProductRespDTO getProductById(UUID id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> {
                    return new NotFoundException(
                            String.format("Id %s not found!", id)
                    );
                });

        ProductRespDTO productResponse = productMapper.toDto(product);
        return productResponse;
    }

    @Override
    public ProductRespDTO createProduct(ProductReqDTO request) {

        // in this example, foreign key "category_id" in Product model cannot be NULL
        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> {
                    return new NotFoundException(
                            String.format("Id %s not found!", request.getCategoryId())
                    );
                });

        Product productModel = productMapper.toEntity(request);
        productModel.setCategory(category);

        Product newProductModel = productRepository.save(productModel);

        ProductRespDTO productResponse = productMapper.toDto(newProductModel);
        return productResponse;
    }

    @Override
    public ProductRespDTO updateProduct(UUID id, ProductReqDTO request) {
        Product existedProduct = productRepository
                .findById(id)
                .orElseThrow(() -> {
                    return new NotFoundException(
                            String.format("Id %s not found!", request.getCategoryId())
                    );
                });

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> {
                    return new NotFoundException(
                            String.format("Id %s not found!", request.getCategoryId())
                    );
                });

        productMapper.updateEntityFromDto(request, existedProduct);
        existedProduct.setCategory(category);

        Product newProductModel = productRepository.save(existedProduct);
        ProductRespDTO productResponse = productMapper.toDto(newProductModel);
        return productResponse;
    }
}
