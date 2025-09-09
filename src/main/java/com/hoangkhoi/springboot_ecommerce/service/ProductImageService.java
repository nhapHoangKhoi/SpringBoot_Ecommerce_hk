package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.response.ProductImageRespDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductImageService {
    ProductImageRespDTO addImageToProduct(UUID productId, MultipartFile file);
    // List<ProductImageRespDTO> addImagesToProduct(UUID productId, List<MultipartFile> files);
    List<ProductImageRespDTO> getImagesByProductId(UUID productId);
    void deleteImage(UUID imageId);
}
