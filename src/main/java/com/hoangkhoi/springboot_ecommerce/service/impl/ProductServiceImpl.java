package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.request.ProductReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import com.hoangkhoi.springboot_ecommerce.exception.ExceptionMessages;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.mapper.ProductMapper;
import com.hoangkhoi.springboot_ecommerce.model.Category;
import com.hoangkhoi.springboot_ecommerce.model.Product;
import com.hoangkhoi.springboot_ecommerce.repository.CategoryRepository;
import com.hoangkhoi.springboot_ecommerce.repository.ProductRepository;
import com.hoangkhoi.springboot_ecommerce.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    // @Override
    // public List<ProductRespDTO> getAllProducts() {
    //     List<ProductRespDTO> products = productRepository
    //             .findAll()
    //             .stream()
    //             .map(productMapper::toDto)
    //             .toList();
    //
    //     return products;
    // }
    @Override
    public Page<ProductRespDTO> getAllProducts(int page, int limit) {
        // ----- Pagination ----- //
        if(page <= 0) {
            page = 1;
        }

        long totalRecords = productRepository.count();
        int totalPages = (int) Math.ceil((double) totalRecords / limit);
        if(page > totalPages && totalPages > 0) {
            page = totalPages;
        }

        Pageable pageable = PageRequest.of(
                page - 1, // spring data uses 0-based page index, so subtract 1
                limit,
                Sort.by("createdAt").descending()
        );
        // ----- End pagination ----- //

        Page<ProductRespDTO> products = productRepository
                .findAll(pageable)
                .map(productMapper::toDto);

        return products;
    }

    @Override
    public Page<ProductRespDTO> getProductsByFilters(String productName, UUID categoryId, int page, int limit) {
        // ----- Pagination ----- //
        if(page <= 0) {
            page = 1;
        }

        long totalRecords = productRepository.count();
        int totalPages = (int) Math.ceil((double) totalRecords / limit);
        if(page > totalPages && totalPages > 0) {
            page = totalPages;
        }

        Pageable pageable = PageRequest.of(
                page - 1, // spring data uses 0-based page index, so subtract 1
                limit,
                Sort.by("createdAt").descending()
        );
        // ----- End pagination ----- //

        Page<Product> products;

        if(categoryId != null) {
            // filter by category + name
            products = productRepository.findByCategoryIdAndNameContainingIgnoreCase(categoryId, productName, pageable);
        }
        else {
            // only filter by name
            products = productRepository.findByNameContainingIgnoreCase(productName, pageable);
        }

        Page<ProductRespDTO> response = products.map(productMapper::toDto);
        return response;
    }

    @Override
    public Page<ProductRespDTO> getFeaturedProducts() {
        // ----- Pagination ----- //
        int page = 1;
        int limit = 4;

        Pageable pageable = PageRequest.of(
                page - 1, // spring data uses 0-based page index, so subtract 1
                limit,
                Sort.by("updatedAt").descending()
        );
        // ----- End pagination ----- //

        Page<ProductRespDTO> featuredProducts = productRepository
                .findByIsFeaturedTrueAndIsDeletedFalse(pageable)
                .map(productMapper::toDto);

        return featuredProducts;
    }

    @Override
    public ProductRespDTO getProductById(UUID id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> {
                    return new NotFoundException(
                            String.format("Id " + ExceptionMessages.NOT_FOUND, id)
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
                            String.format("Id " + ExceptionMessages.NOT_FOUND, request.getCategoryId())
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
                            String.format("Id " + ExceptionMessages.NOT_FOUND, request.getCategoryId())
                    );
                });

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> {
                    return new NotFoundException(
                            String.format("Id " + ExceptionMessages.NOT_FOUND, request.getCategoryId())
                    );
                });

        productMapper.updateEntityFromDto(request, existedProduct);
        existedProduct.setCategory(category);

        Product newProductModel = productRepository.save(existedProduct);
        ProductRespDTO productResponse = productMapper.toDto(newProductModel);
        return productResponse;
    }

    @Override
    public void deleteProductSoft(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundException(
                            String.format("Id " + ExceptionMessages.NOT_FOUND, id)
                    );
                });

        product.setDeleted(true);
        productRepository.save(product);
    }
}
