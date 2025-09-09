package com.hoangkhoi.springboot_ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadAssetToCloudinary(MultipartFile file);
    void deleteAssetFromCloudinary(String imageUrl);
}
