package com.hoangkhoi.springboot_ecommerce.service;

import com.hoangkhoi.springboot_ecommerce.dto.request.UserSignUpReqDTO;
import com.hoangkhoi.springboot_ecommerce.dto.response.UserSignUpRespDTO;

public interface UserService {
    UserSignUpRespDTO signUp(UserSignUpReqDTO request);
}
