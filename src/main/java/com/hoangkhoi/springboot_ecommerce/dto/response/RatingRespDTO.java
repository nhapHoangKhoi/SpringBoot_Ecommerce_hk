package com.hoangkhoi.springboot_ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class RatingRespDTO {
    private UUID id;
    private int ratingValue;
    private String comment;
    private String email;
    private LocalDateTime createdAt;
}
