package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hoangkhoi.springboot_ecommerce.exception.BadRequestException;
import com.hoangkhoi.springboot_ecommerce.exception.ExceptionMessages;
import com.hoangkhoi.springboot_ecommerce.service.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadAssetToCloudinary(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");
            return imageUrl;
        }
        catch (IOException io) {
            throw new BadRequestException(ExceptionMessages.IMAGE_UPLOAD_FAILED);
        }
    }

    // https://res.cloudinary.com/rjklkimn/image/upload/v1741568358/abcdefgh.png
    @Override
    public void deleteAssetFromCloudinary(String imageUrl) {
        try {
            String publicId = extractPublicId(imageUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }
        catch (IOException e) {
            throw new BadRequestException(ExceptionMessages.IMAGE_DELETE_FAILED);
        }
    }

    private String extractPublicId(String imageUrl) {
        // pop out the last then split by symbol dot "."
        String[] parts = imageUrl.split("/");
        return parts[parts.length - 1].split("\\.")[0];
    }
}
