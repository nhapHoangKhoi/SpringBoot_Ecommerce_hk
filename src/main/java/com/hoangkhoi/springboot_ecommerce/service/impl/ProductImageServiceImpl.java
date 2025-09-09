package com.hoangkhoi.springboot_ecommerce.service.impl;

import com.hoangkhoi.springboot_ecommerce.dto.response.ProductImageRespDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.ProductRespDTO;
import com.hoangkhoi.springboot_ecommerce.exception.ExceptionMessages;
import com.hoangkhoi.springboot_ecommerce.exception.NotFoundException;
import com.hoangkhoi.springboot_ecommerce.mapper.ProductImageMapper;
import com.hoangkhoi.springboot_ecommerce.model.Product;
import com.hoangkhoi.springboot_ecommerce.model.ProductImage;
import com.hoangkhoi.springboot_ecommerce.repository.ProductImageRepository;
import com.hoangkhoi.springboot_ecommerce.repository.ProductRepository;
import com.hoangkhoi.springboot_ecommerce.service.CloudinaryService;
import com.hoangkhoi.springboot_ecommerce.service.ProductImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CloudinaryService cloudinaryService;
    private final ProductImageMapper productImageMapper;

    @Override
    public ProductImageRespDTO addImageToProduct(UUID productId, MultipartFile file) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Id " + ExceptionMessages.NOT_FOUND, productId))
                );

        // upload the image to Cloudinary and get the URL
        String imageUrl = cloudinaryService.uploadAssetToCloudinary(file);

        ProductImage productImageModel = new ProductImage();
        productImageModel.setProduct(product);
        productImageModel.setImageUrl(imageUrl);

        ProductImage newProductImageModel = productImageRepository.save(productImageModel);

        ProductImageRespDTO productImageResponse = productImageMapper.toDto(newProductImageModel);
        return productImageResponse;
    }

    // @Override
    // @Transactional
    // public List<ProductImageRespDTO> addImagesToProduct(UUID productId, List<MultipartFile> files) {
    //     Product product = productRepository.findById(productId)
    //             .orElseThrow(() -> new NotFoundException(
    //                     String.format("Id " + ExceptionMessages.NOT_FOUND, productId))
    //             );
    //
    //     List<ProductImageRespDTO> responses = new ArrayList<>();
    //
    //     for(MultipartFile file : files) {
    //         // upload the image to Cloudinary and get the URL
    //         String imageUrl = cloudinaryService.uploadAssetToCloudinary(file);
    //
    //         ProductImage productImageModel = new ProductImage();
    //         productImageModel.setProduct(product);
    //         productImageModel.setImageUrl(imageUrl);
    //
    //         ProductImage savedImage = productImageRepository.save(productImageModel);
    //
    //         responses.add(productImageMapper.toDto(savedImage));
    //     }
    //
    //     return responses;
    // }

    @Override
    public List<ProductImageRespDTO> getImagesByProductId(UUID productId) {
        List<ProductImageRespDTO> images = productImageRepository.findByProductId(productId)
                .stream()
                .map(productImageMapper::toDto)
                .toList();

        return images;
    }

    @Override
    public void deleteImage(UUID imageId) {
        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("ImageId " + ExceptionMessages.NOT_FOUND, imageId))
                );

        cloudinaryService.deleteAssetFromCloudinary(productImage.getImageUrl());

        productImageRepository.delete(productImage);
    }
}
