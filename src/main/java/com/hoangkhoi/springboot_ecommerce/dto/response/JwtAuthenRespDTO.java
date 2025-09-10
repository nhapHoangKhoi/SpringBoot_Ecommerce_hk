package com.hoangkhoi.springboot_ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenRespDTO {
    private String accessToken;
    private String tokenType = "Bearer";
}
