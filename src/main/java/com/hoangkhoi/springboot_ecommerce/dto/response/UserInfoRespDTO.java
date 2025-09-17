package com.hoangkhoi.springboot_ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserInfoRespDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
}
